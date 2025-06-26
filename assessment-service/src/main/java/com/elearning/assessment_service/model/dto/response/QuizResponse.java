package com.elearning.assessment_service.model.dto.response;
import lombok.Data;
import java.util.List;
@Data public class QuizResponse { private Long id; private Long courseId; private String title; private List<QuestionResponse> questions; }
