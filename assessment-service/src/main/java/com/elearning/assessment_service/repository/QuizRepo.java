package com.elearning.assessment_service.repository;

import com.elearning.assessment_service.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByCourseId(Long courseId);
}