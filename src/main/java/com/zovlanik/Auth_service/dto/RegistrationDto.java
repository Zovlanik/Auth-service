package com.zovlanik.Auth_service.dto;

import com.example.common.AddressDto;
import com.example.common.IndividualDto;
import com.example.common.UserDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    private KeyCloakUserDto keyCloakUserDto;
    private AddressDto addressDto;
    private UserDto userDto;
    private IndividualDto individualDto;

}
