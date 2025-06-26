package com.elearning.assessment_service.model.dto.request;
import lombok.Data;
import java.util.List;
@Data public class QuestionRequest { private String questionText; private List<OptionRequest> options; }
