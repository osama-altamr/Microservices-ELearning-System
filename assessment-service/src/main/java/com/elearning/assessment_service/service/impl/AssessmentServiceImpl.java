package com.elearning.assessment_service.service.impl;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.elearning.assessment_service.model.mapper.QuizMapper;
import com.elearning.assessment_service.model.dto.client.UpdateEnrollmentStatusRequest;
import com.elearning.assessment_service.model.dto.request.AnswerRequest;
import com.elearning.assessment_service.model.dto.request.OptionRequest;
import com.elearning.assessment_service.model.dto.request.QuestionRequest;
import com.elearning.assessment_service.model.dto.request.QuizRequest;
import com.elearning.assessment_service.model.dto.request.SubmissionRequest;
import com.elearning.assessment_service.model.dto.response.QuizForLearnerResponse;
import com.elearning.assessment_service.model.dto.response.QuizResponse;
import com.elearning.assessment_service.model.dto.response.SubmissionResponse;
import com.elearning.assessment_service.model.entity.Option;
import com.elearning.assessment_service.model.entity.Question;
import com.elearning.assessment_service.model.entity.Quiz;
import com.elearning.assessment_service.model.entity.Submission;
import com.elearning.assessment_service.model.entity.SubmittedAnswer;
import com.elearning.assessment_service.model.enums.SubmissionStatus;
import com.elearning.assessment_service.repository.QuizRepo;
import com.elearning.assessment_service.repository.SubmissionRepo;
import com.elearning.assessment_service.service.AssessmentService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

    private final QuizRepo quizRepo;
    private final SubmissionRepo submissionRepo;
    private final QuizMapper quizMapper;

    private final RestTemplate restTemplate;

  @Override
    @Transactional 
    public QuizResponse createQuiz(QuizRequest request) {
        
        Quiz quiz = Quiz.builder()
                .courseId(request.getCourseId())
                .title(request.getTitle())
                .questions(new ArrayList<>())
                .build();

        for (QuestionRequest questionReq : request.getQuestions()) {
            
            Question question = Question.builder()
                    .questionText(questionReq.getQuestionText())
                    .options(new ArrayList<>())
                    .quiz(quiz) // <-- **نقطة مهمة**: نربط السؤال بالاختبار الأب
                    .build();

            for (OptionRequest optionReq : questionReq.getOptions()) {
                
                Option option = Option.builder()
                        .optionText(optionReq.getOptionText())
                        .isCorrect(optionReq.getIsCorrect())
                        .question(question) // <-- **نقطة مهمة**: نربط الخيار بالسؤال الأب له
                        .build();
                question.getOptions().add(option);
            }

            quiz.getQuestions().add(question);
        }

       
        Quiz savedQuiz = quizRepo.save(quiz);
        return quizMapper.toDto(savedQuiz);
    }

    @Override
    public QuizForLearnerResponse getQuizForTaking(Long courseId) {
        Quiz quiz = quizRepo.findByCourseId(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found for course: " + courseId));
        return quizMapper.toQuizForLearnerDto(quiz);
    }

    @Override
    @Transactional
    public SubmissionResponse submitQuiz(Long quizId, SubmissionRequest request) {
        Long userId = getCurrentUserId();
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id: " + quizId));

     Map<Long, Long> answerKey = quiz.getQuestions().stream()
            .collect(Collectors.toMap(
                    Question::getId,
                    q -> q.getOptions().stream()
                            .filter(Option::getIsCorrect)
                            .findFirst()
                            .map(Option::getId)
                            .orElseThrow(() -> new IllegalStateException("Quiz is malformed: Question ID " + q.getId() + " has no correct option."))
            ));
  
            Submission submission = Submission.builder()
            .quizId(quizId)
            .userId(userId)
            .build();

    int score = 0;
        for (AnswerRequest answerRequest : request.getAnswers()) {
        Long questionId = answerRequest.getQuestionId();
        Long selectedOptionId = answerRequest.getSelectedOptionId();

    
        if (answerKey.getOrDefault(questionId, -1L).equals(selectedOptionId)) {
            score++;
        }

        SubmittedAnswer submittedAnswer = SubmittedAnswer.builder()
                .questionId(questionId)
                .selectedOptionId(selectedOptionId)
                .build();
        
        submission.addSubmittedAnswer(submittedAnswer);
    }
  
    int totalQuestions = quiz.getQuestions().size();
    double passingPercentage = 0.5; 
    SubmissionStatus status = (score >= totalQuestions * passingPercentage) ? SubmissionStatus.PASSED : SubmissionStatus.FAILED;

    submission.setScore(score);
    submission.setStatus(status);

    if(status == SubmissionStatus.PASSED){
                        UpdateEnrollmentStatusRequest updateRequest = UpdateEnrollmentStatusRequest.builder()
                        .userId(userId)
                        .courseId(quiz.getCourseId())
                        .newStatus("COMPLETED")
                        .build();
                        
                        System.out.println(updateRequest);
        restTemplate.put(
            "http://ENROLLMENT-SERVICE/api/v1/enrollments/status",
            updateRequest
        );
    }
    Submission savedSubmission = submissionRepo.save(submission);

    SubmissionResponse response = new SubmissionResponse();
    response.setId(savedSubmission.getId());
    response.setQuizId(savedSubmission.getQuizId());
    response.setUserId(savedSubmission.getUserId());
    response.setScore(savedSubmission.getScore());
    response.setTotalQuestions(totalQuestions);
    response.setStatus(savedSubmission.getStatus());
    response.setSubmittedAt(savedSubmission.getSubmittedAt());
 return response;   
    }

    private Long getCurrentUserId() {
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        return Long.parseLong(userIdStr);
    }
    
}