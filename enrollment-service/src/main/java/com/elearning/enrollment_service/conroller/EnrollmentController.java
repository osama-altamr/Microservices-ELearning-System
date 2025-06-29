package com.elearning.enrollment_service.conroller;

import com.elearning.enrollment_service.model.dto.request.EnrollmentRequest;
import com.elearning.enrollment_service.model.dto.request.UpdateEnrollmentStatusRequest;
import com.elearning.enrollment_service.service.EnrollmentService;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService service;

    @PostMapping("/enrollments")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> createEnrollment(@RequestBody EnrollmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createEnrollment(request));
    }

    @PutMapping("/enrollments/status")
    public ResponseEntity<Void> updateStatus(@RequestBody UpdateEnrollmentStatusRequest request) throws InterruptedException {
    // --- إضافة تأخير لمحاكاة خدمة بطيئة ---
    System.out.println("ENROLLMENT-SERVICE: Received update request, simulating delay...");
    // تأخير لمدة 5 ثواني
    TimeUnit.SECONDS.sleep(5); 
    System.out.println("ENROLLMENT-SERVICE: Delay finished. Processing request.");

        service.updateEnrollmentStatus(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/enrollments/my-enrollments")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getMyEnrollments() {
            System.out.println("ENROLLMENT-SERVICE: Received update request, simulating delay...");
        return ResponseEntity.ok(service.findMyEnrollments());
    }

    @GetMapping("/users/{userId}/enrollments")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getEnrollmentsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findEnrollmentsByUserId(userId));
    }
    
    // An additional useful endpoint for an INSTRUCTOR to see students in their course.
    @GetMapping("/courses/{courseId}/enrollments")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<?> getEnrollmentsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(service.findEnrollmentsByCourseId(courseId));
    }
}