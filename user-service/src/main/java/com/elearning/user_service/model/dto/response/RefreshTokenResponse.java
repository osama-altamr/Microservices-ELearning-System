package com.elearning.user_service.model.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponse {
    private String token;
    private String refreshToken;
}
