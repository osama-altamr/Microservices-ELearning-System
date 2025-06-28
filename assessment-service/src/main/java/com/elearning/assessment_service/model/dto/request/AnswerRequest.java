package com.elearning.assessment_service.model.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest { private Long questionId; private Long selectedOptionId; }
