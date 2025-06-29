package com.elearning.enrollment_service.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.elearning.enrollment_service.model.dto.client.response.CourseResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceClient {

    private final RestTemplate restTemplate;

    public Optional<CourseResponse> getCourseById(Long courseId) {
        log.info("Fetching course details for courseId: {}", courseId);
        CourseResponse course = restTemplate.getForObject(
            "http://COURSE-SERVICE/api/v1/courses/{id}",
            CourseResponse.class,
            courseId 
        );
        
        return Optional.ofNullable(course);
    }
}