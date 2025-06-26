package com.elearning.assessment_service.service;

import com.elearning.assessment_service.model.dto.request.QuizRequest;
import com.elearning.assessment_service.model.dto.request.SubmissionRequest;
import com.elearning.assessment_service.model.dto.response.QuizForLearnerResponse;
import com.elearning.assessment_service.model.dto.response.QuizResponse;
import com.elearning.assessment_service.model.dto.response.SubmissionResponse;

public interface AssessmentService {
    // For INSTRUCTOR to create a quiz
    QuizResponse createQuiz(QuizRequest request);
    
    // For LEARNER to get a quiz to take (without correct answers)
    QuizForLearnerResponse getQuizForTaking(Long courseId);

    // For LEARNER to submit their answers
    SubmissionResponse submitQuiz(Long quizId, SubmissionRequest request);
}