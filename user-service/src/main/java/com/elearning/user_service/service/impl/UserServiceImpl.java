package com.elearning.user_service.service.impl;

import com.elearning.user_service.model.dto.request.AdminRequest;
import com.elearning.user_service.model.dto.response.UserResponse;
import com.elearning.user_service.model.entity.User;
import com.elearning.user_service.model.mapper.UserMapper;
import com.elearning.user_service.repository.UserRepo;
import com.elearning.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo repo;
    final private UserMapper mapper;

    @Override
    public List<UserResponse> findAll() {
        System.out.println("List Users");
        return mapper.toDtos(repo.findAll());
    }

    @Override
    public UserResponse getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return mapper.toDto(currentUser);
    }

    @Override
    public UserResponse findById(Long id) {
        Optional<User> user = repo.findById(id);
        return user.map(mapper::toDto).orElse(null);
    }

    @Override
    public UserResponse save(AdminRequest request) {
        User userSaved = repo.save(mapper.toEntity(request));
        return  mapper.toDto(userSaved);
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> user = repo.findById(id);
        user.ifPresent(repo::delete);
    }

}
