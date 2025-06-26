package com.elearning.course_service.service;

import com.elearning.course_service.model.dto.request.AdminCourseUpdateRequest;
import com.elearning.course_service.model.dto.request.CourseRequest;
import com.elearning.course_service.model.dto.response.CourseResponse;
import java.util.List;

public interface CourseService {
    List<CourseResponse> findAllApprovedCourses();

    List<CourseResponse> findMyCourses();

    List<CourseResponse> findAllPendingCourses();

    CourseResponse createCourse(CourseRequest request);

    CourseResponse updateCourseStatus(Long courseId, AdminCourseUpdateRequest request);

    CourseResponse findById(Long id);

    void deleteById(Long id);
}