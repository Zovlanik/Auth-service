package com.zovlanik.Auth_service.service;

import com.example.common.IndividualDto;
import com.example.common.UserDto;
import com.zovlanik.Auth_service.dto.AccessTokenDto;
import com.zovlanik.Auth_service.dto.AuthDto;
import com.zovlanik.Auth_service.dto.KeyCloakUserDto;
import com.zovlanik.Auth_service.dto.RegistrationDto;
import com.zovlanik.Auth_service.keycloak.KeyCloakClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AddressService addressService;
    private final UserService userService;
    private final IndividualsService individualsService;
    private final KeyCloakClient keyCloakClient;

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

    public Mono<AccessTokenDto> refreshToken(String refreshToken) {
        return keyCloakClient.refreshToken(refreshToken);
    }

}
