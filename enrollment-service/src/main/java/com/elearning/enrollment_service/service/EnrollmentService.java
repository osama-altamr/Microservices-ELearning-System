package com.elearning.enrollment_service.service;

import com.elearning.enrollment_service.model.dto.client.response.CourseResponse;
import com.elearning.enrollment_service.model.dto.request.EnrollmentRequest;
import com.elearning.enrollment_service.model.dto.request.UpdateEnrollmentStatusRequest;
import com.elearning.enrollment_service.model.dto.response.EnrollmentResponse;
import java.util.List;

public interface EnrollmentService {
    EnrollmentResponse createEnrollment(EnrollmentRequest request, CourseResponse course);
    // For a LEARNER to view their own enrollments
    List<EnrollmentResponse> findMyEnrollments();

    // For an ADMIN to view enrollments of a specific user
    List<EnrollmentResponse> findEnrollmentsByUserId(Long userId);
    
    // For an INSTRUCTOR to view who is enrolled in their course
    List<EnrollmentResponse> findEnrollmentsByCourseId(Long courseId);

    void updateEnrollmentStatus(UpdateEnrollmentStatusRequest request);
}