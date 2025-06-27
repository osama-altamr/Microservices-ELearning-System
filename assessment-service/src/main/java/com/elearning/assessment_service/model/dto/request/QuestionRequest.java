package com.elearning.assessment_service.model.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class 
QuestionRequest { private String questionText; private List<OptionRequest> options; }
