package com.elearning.enrollment_service.model.dto.response;

import com.elearning.enrollment_service.model.enums.EnrollmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EnrollmentResponse {
    private Long id;
    private Long userId;
    private Long courseId;
    private EnrollmentStatus status;
    private LocalDateTime enrollmentDate;
}