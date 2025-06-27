package com.elearning.enrollment_service.service.impl;

import com.elearning.enrollment_service.model.dto.request.EnrollmentRequest;
import com.elearning.enrollment_service.model.dto.request.UpdateEnrollmentStatusRequest;
import com.elearning.enrollment_service.model.dto.response.EnrollmentResponse;
import com.elearning.enrollment_service.model.entity.Enrollment;
import com.elearning.enrollment_service.model.enums.EnrollmentStatus;
import com.elearning.enrollment_service.model.mapper.EnrollmentMapper;
import com.elearning.enrollment_service.repository.EnrollmentRepo;
import com.elearning.enrollment_service.service.EnrollmentService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepo repo;
    private final EnrollmentMapper mapper;

    @Override
public void updateEnrollmentStatus(UpdateEnrollmentStatusRequest request) {
    // ابحث عن الاشتراك بناءً على userId و courseId
    Enrollment enrollment = repo.findByUserIdAndCourseId(request.getUserId(), request.getCourseId())
            .orElseThrow(() -> new EntityNotFoundException(
                "Enrollment not found for user " + request.getUserId() + " and course " + request.getCourseId()
            ));
    
    // قم بتحديث الحالة واحفظ التغييرات
    enrollment.setStatus(request.getNewStatus());
    repo.save(enrollment);
    
    // يمكنك إضافة log هنا للتأكيد
    System.out.println("Updated enrollment status for user {} in course {} to {}" +  request.getUserId().toString()+ " " + request.getCourseId().toString()+ " " + request.getNewStatus().toString());
}

    @Override
    public EnrollmentResponse createEnrollment(EnrollmentRequest request) {
        Long userId = getCurrentUserId();

        // Check if the user is already enrolled
        repo.findByUserIdAndCourseId(userId, request.getCourseId()).ifPresent(e -> {
            throw new EntityExistsException("User is already enrolled in this course.");
        });

        Enrollment enrollment = Enrollment.builder()
                .userId(userId)
                .courseId(request.getCourseId())
                .status(EnrollmentStatus.IN_PROGRESS)
                .build();
        
        Enrollment savedEnrollment = repo.save(enrollment);
        return mapper.toDto(savedEnrollment);
    }

    @Override
    public List<EnrollmentResponse> findMyEnrollments() {
        Long userId = getCurrentUserId();
        List<Enrollment> enrollments = repo.findByUserId(userId);
        return mapper.toDtos(enrollments);
    }

    @Override
    public List<EnrollmentResponse> findEnrollmentsByUserId(Long userId) {
        // This is typically for an admin
        List<Enrollment> enrollments = repo.findByUserId(userId);
        return mapper.toDtos(enrollments);
    }
    
    @Override
    public List<EnrollmentResponse> findEnrollmentsByCourseId(Long courseId) {
        // This is for an instructor to see their students
        List<Enrollment> enrollments = repo.findByCourseId(courseId);
        return mapper.toDtos(enrollments);
    }

    // Helper method to get the current user's ID from the security context (JWT)
    private Long getCurrentUserId() {
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        return Long.parseLong(userIdStr);
    }
}