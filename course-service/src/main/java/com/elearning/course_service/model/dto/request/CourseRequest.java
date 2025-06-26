package com.elearning.course_service.model.dto.request;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CourseRequest {
    private String title;
    private String description;
    private BigDecimal price;
}
