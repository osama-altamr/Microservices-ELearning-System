package com.elearning.course_service.service.impl;

import com.elearning.course_service.model.dto.request.AdminCourseUpdateRequest;
import com.elearning.course_service.model.dto.request.CourseRequest;
import com.elearning.course_service.model.dto.response.CourseResponse;
import com.elearning.course_service.model.entity.Course;
import com.elearning.course_service.model.enums.CourseStatus;
import com.elearning.course_service.model.mapper.CourseMapper;
import com.elearning.course_service.repository.CourseRepo;
import com.elearning.course_service.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepo repo;
    private final CourseMapper mapper;

    @Override
    public List<CourseResponse> findAllApprovedCourses() {
        List<Course> courses = repo.findByStatus(CourseStatus.APPROVED);
        return mapper.toDtos(courses);
    }

    @Override
    public List<CourseResponse> findMyCourses() {
        Long instructorId = getCurrentUserId(); // Helper method to get user ID from token
        List<Course> courses = repo.findByInstructorId(instructorId);
        return mapper.toDtos(courses);
    }

    @Override
    public List<CourseResponse> findAllPendingCourses() {
        List<Course> courses = repo.findByStatus(CourseStatus.PENDING_APPROVAL);
        return mapper.toDtos(courses);
    }

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        Long instructorId = getCurrentUserId();
        Course course = mapper.toEntity(request);
        course.setInstructorId(instructorId);
        course.setStatus(CourseStatus.PENDING_APPROVAL); // New courses are always pending
        Course savedCourse = repo.save(course);
        return mapper.toDto(savedCourse);
    }

    @Override
    public CourseResponse updateCourseStatus(Long courseId, AdminCourseUpdateRequest request) {
        Course course = repo.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
        
        // Admin can only approve or reject
        if (request.getStatus() == CourseStatus.APPROVED || request.getStatus() == CourseStatus.REJECTED) {
            course.setStatus(request.getStatus());
        } else {
            // Or throw an exception
            return null; 
        }

        Course updatedCourse = repo.save(course);
        return mapper.toDto(updatedCourse);
    }

    @Override
    public CourseResponse findById(Long id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        // You might want to add logic here to check if the user is an admin or the owner instructor
        repo.deleteById(id);
    }

    // Helper method to extract User ID from the JWT token
    private Long getCurrentUserId() {
        // In a real app, you would parse the JWT from the SecurityContext
        // For now, we assume the 'name' of the authentication principal is the user ID string
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        return Long.parseLong(userIdStr);
    }
}