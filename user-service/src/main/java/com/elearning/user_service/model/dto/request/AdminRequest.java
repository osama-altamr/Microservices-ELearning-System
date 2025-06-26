package com.elearning.user_service.model.dto.request;

import com.elearning.user_service.model.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminRequest {
    private String fullName;
    private String email;
    private String password;
    private Role role;   
}
