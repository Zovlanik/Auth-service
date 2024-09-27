package com.zovlanik.Auth_service.service;

import com.example.common.AddressDto;
import com.example.common.IndividualDto;
import com.example.common.UserDto;
import com.zovlanik.Auth_service.dto.*;
import com.zovlanik.Auth_service.entity.Address;
import com.zovlanik.Auth_service.entity.Individual;
import com.zovlanik.Auth_service.entity.User;
import com.zovlanik.Auth_service.keycloak.KeycloakClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    AddressService addressService;

    @Mock
    UserService userService;

    @Mock
    IndividualsService individualsService;

    @Mock
    KeycloakClient keyCloakClient;

    @InjectMocks
    AuthService authService;

    private RegistrationDto registrationDto;

    private AddressDto addressDto;
    private Address address;
    private UserDto userDto;
    private IndividualDto individualDto;
    private KeyCloakUserDto keyCloakUserDto;
    private AccessTokenDto accessTokenDto;

    @BeforeEach
    void setUp() {
        // Инициализация тестовых данных
        addressDto = new AddressDto();
        addressDto.setAddress("Test Address");

        userDto = new UserDto();
        userDto.setFirstName("Test");
        userDto.setLastName("User");

        individualDto = new IndividualDto();
        individualDto.setPassportNumber("123456");

        keyCloakUserDto = KeyCloakUserDto.builder()
                .username("testuser")
                .password("password")
                .build();

        registrationDto = RegistrationDto.builder()
                .addressDto(addressDto)
                .userDto(userDto)
                .individualDto(individualDto)
                .keyCloakUserDto(keyCloakUserDto)
                .build();

        accessTokenDto = AccessTokenDto.builder()
                .accessToken("access_token")
                .refreshToken("refresh_token")
                .build();
    }

    @Test
    void registration_shouldReturnAccessToken() {
        // Мокаем вызовы зависимостей
        when(addressService.createAddress(any(AddressDto.class))).thenReturn(Mono.just(new Address()));
        when(userService.createUser(any(UserDto.class))).thenReturn(Mono.just(new User()));
        when(individualsService.createIndividual(any(IndividualDto.class))).thenReturn(Mono.just(new Individual()));
        when(keyCloakClient.registration(any(KeyCloakUserDto.class))).thenReturn(Mono.just(accessTokenDto));

        // Выполняем регистрацию
        Mono<AccessTokenDto> result = authService.registration(registrationDto);

        // Проверка результата
        StepVerifier.create(result)
                .expectNext(accessTokenDto)
                .verifyComplete();
    }

    @Test
    void authorization_shouldReturnAccessToken() {
        AuthDto authDto = new AuthDto();
        authDto.setUsername("test");
        authDto.setPassword("password");

        when(keyCloakClient.authorization(any(KeyCloakUserDto.class))).thenReturn(Mono.just(accessTokenDto));

        Mono<AccessTokenDto> result = authService.authorization(authDto);

        StepVerifier.create(result)
                .expectNext(accessTokenDto)
                .verifyComplete();
    }

    @Test
    void refreshToken_shouldReturnNewAccessToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken("refresh_token");

        when(keyCloakClient.refreshToken(any(RefreshToken.class))).thenReturn(Mono.just(accessTokenDto));

        Mono<AccessTokenDto> result = authService.refreshToken(refreshToken);

        StepVerifier.create(result)
                .expectNext(accessTokenDto)
                .verifyComplete();
    }
}
