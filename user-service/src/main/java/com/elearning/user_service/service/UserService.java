package com.elearning.user_service.service;

import com.elearning.user_service.model.dto.request.AdminRequest;
import com.elearning.user_service.model.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();
    UserResponse getMe();
    UserResponse findById(Long id);
    // UserResponse registerInstructor(UserRequest request);
    UserResponse save(AdminRequest request);
    // UserResponse update(Long id,UserRequest request);
    void deleteById(Long id);
}
