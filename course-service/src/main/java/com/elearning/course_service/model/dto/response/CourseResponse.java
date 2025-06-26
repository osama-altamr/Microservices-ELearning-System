package com.elearning.course_service.model.dto.response;

import com.elearning.course_service.model.enums.CourseStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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