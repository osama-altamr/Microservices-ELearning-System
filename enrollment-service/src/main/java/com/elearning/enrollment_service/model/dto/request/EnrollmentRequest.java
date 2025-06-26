package com.elearning.enrollment_service.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentRequest {
    private Long courseId;
}