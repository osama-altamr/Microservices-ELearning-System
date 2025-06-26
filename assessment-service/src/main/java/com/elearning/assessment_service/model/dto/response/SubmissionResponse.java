package com.elearning.assessment_service.model.dto.response;
import com.elearning.assessment_service.model.enums.SubmissionStatus;
import lombok.Data;
import java.time.LocalDateTime;
@Data public class SubmissionResponse { private Long id; private Long quizId; private Long userId; private Integer score; private Integer totalQuestions; private SubmissionStatus status; private LocalDateTime submittedAt; }