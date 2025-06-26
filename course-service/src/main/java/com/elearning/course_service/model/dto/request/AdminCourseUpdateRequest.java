package com.elearning.course_service.model.dto.request;

import com.elearning.course_service.model.enums.CourseStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminCourseUpdateRequest {
    private CourseStatus status;
}