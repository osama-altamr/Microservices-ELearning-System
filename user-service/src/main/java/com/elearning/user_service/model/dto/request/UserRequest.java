package com.elearning.user_service.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    private String fullName;
    private String email;
    private String password;
}
