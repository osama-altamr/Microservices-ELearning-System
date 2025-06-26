package com.elearning.user_service.model.dto.response;

import com.elearning.user_service.model.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private boolean enabled;
}
