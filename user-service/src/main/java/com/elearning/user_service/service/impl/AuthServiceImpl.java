package com.elearning.user_service.service.impl;

import com.elearning.user_service.config.JwtService;
import com.elearning.user_service.model.dto.request.LoginRequest;
import com.elearning.user_service.model.dto.request.RefreshTokenRequest;
import com.elearning.user_service.model.dto.request.UserRequest;
import com.elearning.user_service.model.dto.response.LoginResponse;
import com.elearning.user_service.model.dto.response.RefreshTokenResponse;
import com.elearning.user_service.model.dto.response.UserResponse;
import com.elearning.user_service.model.entity.User;
import com.elearning.user_service.model.enums.Role;
import com.elearning.user_service.model.mapper.UserMapper;
import com.elearning.user_service.repository.UserRepo;
import com.elearning.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public UserResponse register(UserRequest request) {
        User user = userMapper.toEntity(request);
        User userSaved = userRepo.save(user);
        return userMapper.toDto(userSaved);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = authenticate(request);

        String jwt = jwtService.generateToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        return LoginResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .token(jwt)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        String username = jwtService.extractUsername(request.getRefreshToken());

        User user = userRepo.findByEmail(username).orElseThrow();

        if (jwtService.isTokenValid(request.getRefreshToken(), user)) {
            String jwt = jwtService.generateToken(user);
            return RefreshTokenResponse.builder()
                    .token(jwt)
                    .refreshToken(request.getRefreshToken())
                    .build();
        }
        return null;
    }

    private User authenticate(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        return userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException(request.getEmail()+" Not Found"));
    }

}
