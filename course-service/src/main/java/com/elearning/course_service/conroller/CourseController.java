package com.elearning.course_service.conroller;

import com.elearning.course_service.model.dto.request.AdminCourseUpdateRequest;
import com.elearning.course_service.model.dto.request.CourseRequest;
import com.elearning.course_service.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @GetMapping
    public ResponseEntity<?> findAllApprovedCourses() {
        return ResponseEntity.ok(service.findAllApprovedCourses());
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> findAllPendingCourses() {
        return ResponseEntity.ok(service.findAllPendingCourses());
    }

    @GetMapping("/my-courses")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<?> findMyCourses() {
        return ResponseEntity.ok(service.findMyCourses());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<?> createCourse(@RequestBody CourseRequest request) {
        System.out.println(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCourse(request));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateCourseStatus(@PathVariable("id") Long id, @RequestBody AdminCourseUpdateRequest request) {
        System.out.println("Id:  " + id + "Req: " + request);
        return ResponseEntity.ok(service.updateCourseStatus(id, request));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}