package com.elearning.enrollment_service.model.dto.request;

import com.elearning.enrollment_service.model.enums.EnrollmentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateEnrollmentStatusRequest {
    private Long userId;
    private Long courseId;
    private EnrollmentStatus newStatus;
}