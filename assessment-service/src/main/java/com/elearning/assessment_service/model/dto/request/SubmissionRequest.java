package com.elearning.assessment_service.model.dto.request;
import lombok.Data;
import java.util.List;
@Data public class SubmissionRequest { private List<AnswerRequest> answers; }