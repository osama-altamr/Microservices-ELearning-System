package com.elearning.user_service.model.mapper;

import com.elearning.user_service.model.dto.request.AdminRequest;
import com.elearning.user_service.model.dto.request.UserRequest;
import com.elearning.user_service.model.dto.response.UserResponse;
import com.elearning.user_service.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public abstract UserResponse toDto(User entity);

    public abstract List<UserResponse> toDtos(List<User> entities);

    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "role", constant = "LEARNER")
    @Mapping(target = "password",source = "password",qualifiedByName = "encodePassword")
    public abstract User toEntity(UserRequest request);


    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "password",source = "password",qualifiedByName = "encodePassword")
    public abstract User toEntity(AdminRequest request);

    public abstract void toEntity(@MappingTarget User entity, UserRequest request);

    @Named("encodePassword")
    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

}
