package com.zovlanik.Auth_service.service;

import com.example.common.IndividualDto;
import com.example.common.UserDto;
import com.zovlanik.Auth_service.dto.*;
import com.zovlanik.Auth_service.keycloak.KeycloakClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AddressService addressService;
    private final UserService userService;
    private final IndividualsService individualsService;
    private final KeycloakClient keyCloakClient;

    public Mono<AccessTokenDto> registration(RegistrationDto registrationDto) {
        return addressService.createAddress(registrationDto.getAddressDto())
                .flatMap(address -> {
                    UserDto userDto = registrationDto.getUserDto();
                    userDto.setAddressId(address.getId());
                    return userService.createUser(userDto);
                })
                .flatMap(user -> {
                    IndividualDto individualDto = registrationDto.getIndividualDto();
                    individualDto.setUserId(user.getId());
                    return individualsService.createIndividual(individualDto);
                })
                .flatMap(individual -> keyCloakClient.registration(registrationDto.getKeyCloakUserDto()));
    }

    public Mono<AccessTokenDto> authorization(AuthDto authDto) {
        return keyCloakClient.authorization(KeyCloakUserDto.builder()
                .username(authDto.getUsername())
                .password(authDto.getPassword())
                .build());
    }

    public Mono<AccessTokenDto> refreshToken(RefreshToken refreshToken) {
        return keyCloakClient.refreshToken(refreshToken);
    }

}
