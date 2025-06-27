package com.elearning.assessment_service.conroller;

import com.elearning.assessment_service.model.dto.request.QuizRequest;
import com.elearning.assessment_service.model.dto.request.SubmissionRequest;
import com.elearning.assessment_service.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService service;

    @PostMapping("/quizzes")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<?> createQuiz(@RequestBody QuizRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createQuiz(request));
    }

    @GetMapping("/courses/{courseId}/quiz")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getQuizForTaking(@PathVariable("courseId") Long courseId) {
        System.out.println("CourseId: "+ courseId);
        return ResponseEntity.ok(service.getQuizForTaking(courseId));
    }

    @PostMapping("/quizzes/{quizId}/submit")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> submitQuiz(@PathVariable("quizId") Long quizId, @RequestBody SubmissionRequest request) {
        return ResponseEntity.ok(service.submitQuiz(quizId, request));
    }
}