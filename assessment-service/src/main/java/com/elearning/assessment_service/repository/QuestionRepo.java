package com.elearning.assessment_service.repository;

import com.elearning.assessment_service.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends JpaRepository<Quiz, Long> {}