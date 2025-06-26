package com.elearning.user_service.config;

import com.elearning.user_service.model.dto.request.LoginRequest;
import com.elearning.user_service.model.dto.response.LoginResponse;
import com.elearning.user_service.model.entity.User;
import com.elearning.user_service.model.enums.Role;
import com.elearning.user_service.repository.UserRepo;
import com.elearning.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthService authService;

    @Override
    public void run(String... args) {
        if (userRepo.findAll().isEmpty()) {
            userRepo.save(User.builder()
                    .fullName("Admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("12345678"))
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build());
        }

        LoginResponse response = authService.login(LoginRequest.builder()
                .email("admin@gmail.com")
                .password("12345678")
                .build());

        System.out.println("=========================Admin============================");
        System.out.println("Fullname: " + response.getFullName());
        System.out.println("Email: " + response.getEmail());
        System.out.println("Role: " + response.getRole());
        System.out.println("Token: " + response.getToken());
        System.out.println("refresh: " + response.getRefreshToken());

    }
}
