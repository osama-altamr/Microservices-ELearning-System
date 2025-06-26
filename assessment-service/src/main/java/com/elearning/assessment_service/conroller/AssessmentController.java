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

    // INSTRUCTOR creates a full quiz with questions and answers
    @PostMapping("/quizzes")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<?> createQuiz(@RequestBody QuizRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createQuiz(request));
    }

    // LEARNER gets a quiz for a specific course to take it
    @GetMapping("/courses/{courseId}/quiz")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getQuizForTaking(@PathVariable Long courseId) {
        return ResponseEntity.ok(service.getQuizForTaking(courseId));
    }

    // LEARNER submits their answers for a quiz
    @PostMapping("/quizzes/{quizId}/submit")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> submitQuiz(@PathVariable Long quizId, @RequestBody SubmissionRequest request) {
        return ResponseEntity.ok(service.submitQuiz(quizId, request));
    }
}