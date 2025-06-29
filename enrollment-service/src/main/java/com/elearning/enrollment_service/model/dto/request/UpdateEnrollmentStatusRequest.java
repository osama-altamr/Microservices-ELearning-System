package com.elearning.enrollment_service.model.dto.request;

import com.elearning.enrollment_service.model.enums.EnrollmentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEnrollmentStatusRequest {
    private Long userId;
    private Long courseId;
    private EnrollmentStatus newStatus;
}