package com.elearning.course_service.model.dto.request;

import com.elearning.course_service.model.enums.CourseStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminCourseUpdateRequest {
    private CourseStatus status;
}