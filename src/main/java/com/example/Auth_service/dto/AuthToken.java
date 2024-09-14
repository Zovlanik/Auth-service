package com.example.Auth_service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthToken {
    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
    private String tokenType;
}
