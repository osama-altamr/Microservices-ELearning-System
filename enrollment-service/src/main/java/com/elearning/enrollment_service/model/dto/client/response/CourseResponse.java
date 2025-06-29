package com.elearning.enrollment_service.model.dto.client.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.elearning.enrollment_service.model.dto.client.enums.CourseStatus;

@Data
@Builder
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Long instructorId;
    private CourseStatus status;
    private LocalDateTime createdAt;
}