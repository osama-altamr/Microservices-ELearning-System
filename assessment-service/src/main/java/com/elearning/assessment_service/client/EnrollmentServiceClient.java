package com.elearning.assessment_service.client;

import com.elearning.assessment_service.model.dto.client.UpdateEnrollmentStatusRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentServiceClient {

    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "enrollmentService", fallbackMethod = "updateEnrollmentFallback")
    public void updateEnrollmentStatus(Long userId, Long courseId) {
        log.info("Attempting to update enrollment status for user {} in course {}.", userId, courseId);
        
        UpdateEnrollmentStatusRequest updateRequest = UpdateEnrollmentStatusRequest.builder()
                .userId(userId)
                .courseId(courseId)
                .newStatus("COMPLETED")
                .build();

        restTemplate.put(
            "http://ENROLLMENT-SERVICE/api/v1/enrollments/status",
            updateRequest
        );
        log.info("Successfully requested enrollment status update.");
    }

    public void updateEnrollmentFallback(Long userId, Long courseId, Throwable throwable) {
        log.error(
            "Enrollment service is down or timed out. Fallback executed for user: {}. Error: {}", 
            userId, 
            throwable.getMessage()
        );
    }
}