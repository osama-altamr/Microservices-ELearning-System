package com.elearning.assessment_service.model.mapper;
import com.elearning.assessment_service.model.dto.request.QuizRequest;
import com.elearning.assessment_service.model.dto.response.QuizForLearnerResponse;
import com.elearning.assessment_service.model.dto.response.QuizResponse;
import com.elearning.assessment_service.model.entity.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuizMapper {

    // Convert Quiz entity to QuizResponse DTO
    QuizResponse toDto(Quiz entity);

    QuizForLearnerResponse toQuizForLearnerDto(Quiz entity);


    // Convert List<Quiz> to List<QuizResponse>
    List<QuizResponse> toDtos(List<Quiz> entities);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "questions", ignore = true)
    Quiz toEntity(QuizRequest request);
}