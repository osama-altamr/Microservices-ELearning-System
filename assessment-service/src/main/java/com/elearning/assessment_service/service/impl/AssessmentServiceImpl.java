package com.elearning.assessment_service.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.elearning.assessment_service.model.dto.request.AnswerRequest;
import com.elearning.assessment_service.model.dto.request.QuizRequest;
import com.elearning.assessment_service.model.dto.request.SubmissionRequest;
import com.elearning.assessment_service.model.dto.response.QuizForLearnerResponse;
import com.elearning.assessment_service.model.dto.response.QuizResponse;
import com.elearning.assessment_service.model.dto.response.SubmissionResponse;
import com.elearning.assessment_service.model.entity.Quiz;
import com.elearning.assessment_service.model.entity.Submission;
import com.elearning.assessment_service.model.enums.SubmissionStatus;
import com.elearning.assessment_service.repository.QuizRepo;
import com.elearning.assessment_service.repository.SubmissionRepo;
import com.elearning.assessment_service.service.AssessmentService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {

    private final QuizRepo quizRepo;
    private final SubmissionRepo submissionRepo;
    // Inject Mappers here

    @Override
    @Transactional
    public QuizResponse createQuiz(QuizRequest request) {
        // ... (Logic to map DTO to entities and save)
        // This is complex due to nested objects but is standard mapping logic.
        // You would map QuizRequest -> Quiz, QuestionRequest -> Question, etc.
        // and set the relationships (quiz.setQuestions(...), question.setOptions(...)).
        // Then save the top-level Quiz entity.
        return null; // Return the mapped response
    }

    @Override
    public QuizForLearnerResponse getQuizForTaking(Long courseId) {
        Quiz quiz = quizRepo.findByCourseId(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found for course: " + courseId));
        // Use a mapper to convert Quiz -> QuizForLearnerResponse (which hides correct answers)
        return null; // Return the mapped response
    }

    @Override
    @Transactional
    public SubmissionResponse submitQuiz(Long quizId, SubmissionRequest request) {
        Long userId = getCurrentUserId();
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id: " + quizId));

        // Create an answer key map for easy lookup: Map<questionId, correctOptionId>
        // Map<Long, Long> answerKey = 
        // quiz.getQuestions().stream()
        //         .collect(Collectors.toMap(
        //                 Question::getId,
        //                 q -> q.getOptions().stream()
        //                         .filter(Option::getIsCorrect)
        //                         .findFirst()
        //                         .map(Option::getId)
        //                         .orElse(-1L) // Should not happen in a well-formed quiz
        //         ));
     Map<Long, Long> answerKey =new HashMap<Long, Long>();
        int score = 0;
        for (AnswerRequest answer : request.getAnswers()) {
            if (answerKey.get(answer.getQuestionId()).equals(answer.getSelectedOptionId())) {
                score++;
            }
        }

        int totalQuestions = quiz.getQuestions().size();
        // Define passing score, e.g., 50%
        double passingPercentage = 0.5;
        SubmissionStatus status = (score >= totalQuestions * passingPercentage) ? SubmissionStatus.PASSED : SubmissionStatus.FAILED;

        Submission submission = Submission.builder()
                .quizId(quizId)
                .userId(userId)
                .score(score)
                .status(status)
                .build();
        submissionRepo.save(submission);

        // Here you could make an API call to Enrollment-Service to update enrollment status if PASSED
        
        // Map the submission entity to a response DTO
        SubmissionResponse response = new SubmissionResponse(); // map it properly
        response.setTotalQuestions(totalQuestions);
        return response;
    }

    private Long getCurrentUserId() {
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        return Long.parseLong(userIdStr);
    }
}