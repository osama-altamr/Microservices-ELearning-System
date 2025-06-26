package com.elearning.course_service.model.mapper;

import com.elearning.course_service.model.dto.request.CourseRequest;
import com.elearning.course_service.model.dto.response.CourseResponse;
import com.elearning.course_service.model.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseResponse toDto(Course entity);

    List<CourseResponse> toDtos(List<Course> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instructorId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Course toEntity(CourseRequest request);
}