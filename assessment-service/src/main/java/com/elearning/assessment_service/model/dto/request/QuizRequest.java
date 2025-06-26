package com.elearning.assessment_service.model.dto.request;
import lombok.Data;
import java.util.List;
@Data public class QuizRequest { private Long courseId; private String title; private List<QuestionRequest> questions; }