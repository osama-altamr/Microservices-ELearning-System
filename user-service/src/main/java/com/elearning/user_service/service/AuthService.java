package com.elearning.user_service.service;


import com.elearning.user_service.model.dto.request.LoginRequest;
import com.elearning.user_service.model.dto.request.RefreshTokenRequest;
import com.elearning.user_service.model.dto.request.UserRequest;
import com.elearning.user_service.model.dto.response.LoginResponse;
import com.elearning.user_service.model.dto.response.RefreshTokenResponse;
import com.elearning.user_service.model.dto.response.UserResponse;

public interface AuthService {

    UserResponse register(UserRequest request);

    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
