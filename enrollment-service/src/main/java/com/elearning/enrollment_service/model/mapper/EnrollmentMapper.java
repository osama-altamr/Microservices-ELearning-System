package com.elearning.enrollment_service.model.mapper;

import com.elearning.enrollment_service.model.dto.response.EnrollmentResponse;
import com.elearning.enrollment_service.model.entity.Enrollment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    EnrollmentResponse toDto(Enrollment entity);
    List<EnrollmentResponse> toDtos(List<Enrollment> entities);
}