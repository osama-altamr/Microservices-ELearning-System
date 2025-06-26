package com.elearning.assessment_service.model.dto.response;
import lombok.Data;
import java.util.List;

@Data public class QuizForLearnerResponse { private Long id; private Long courseId; private String title; private List<QuestionForLearnerResponse> questions; }
