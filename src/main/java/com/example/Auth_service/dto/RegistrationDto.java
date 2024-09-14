package com.example.Auth_service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegistrationDto {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
}
