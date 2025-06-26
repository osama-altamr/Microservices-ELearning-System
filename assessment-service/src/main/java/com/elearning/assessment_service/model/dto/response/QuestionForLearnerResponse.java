package com.elearning.assessment_service.model.dto.response;
import lombok.Data;
import java.util.List;
@Data public class QuestionForLearnerResponse { private Long id; private String questionText; private List<OptionForLearnerResponse> options; }