package com.zovlanik.Auth_service.controller;

import com.example.common.AddressDto;
import com.example.common.IndividualDto;
import com.example.common.UserDto;
import com.zovlanik.Auth_service.KeycloakTestContainers;
import com.zovlanik.Auth_service.dto.AccessTokenDto;
import com.zovlanik.Auth_service.dto.AuthDto;
import com.zovlanik.Auth_service.dto.RefreshToken;
import com.zovlanik.Auth_service.dto.RegistrationDto;
import com.zovlanik.Auth_service.service.AddressService;
import com.zovlanik.Auth_service.service.AuthService;
import com.zovlanik.Auth_service.service.IndividualsService;
import com.zovlanik.Auth_service.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;


import static com.zovlanik.Auth_service.utils.MockData.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthRestControllerV1Test extends KeycloakTestContainers{

    @MockBean
    private AddressService addressService;
    @MockBean
    private UserService userService;
    @MockBean
    private IndividualsService individualsService;
    @InjectMocks
    AuthService authService;

    @Autowired
    private WebTestClient webTestClient;

    static String testRefreshToken = "";

    @Test
    @Order(1)
    public void testRegistration() {
        Mockito.when(addressService.createAddress(any(AddressDto.class))).thenReturn(getAddress());
        Mockito.when(userService.createUser(any(UserDto.class))).thenReturn(getUser());
        Mockito.when(individualsService.createIndividual(any(IndividualDto.class))).thenReturn(getIndividual());

        RegistrationDto registrationDto = getRegistrationDto();

        webTestClient.post()
                .uri("/api/v1/auth/registration")
                .contentType(MediaType.APPLICATION_JSON)

                .body(Mono.just(registrationDto), RegistrationDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.access_token").isNotEmpty()
                .jsonPath("$.refresh_token").isNotEmpty();
    }

    @Test
    @Order(2)
    public void testAuthorization() {
        AuthDto authDto = new AuthDto();
        authDto.setUsername("usertest2");
        authDto.setPassword("usertest2");

        webTestClient.post()
                .uri("/api/v1/auth/authorization")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "")
                .body(Mono.just(authDto), AuthDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccessTokenDto.class)
                .value(response -> {
                    Assertions.assertNotNull(response.getAccessToken());
                    Assertions.assertNotNull(response.getRefreshToken());
                    Assertions.assertFalse(response.getAccessToken().isEmpty());
                    Assertions.assertFalse(response.getRefreshToken().isEmpty());
                    testRefreshToken = response.getRefreshToken();
                });
    }

    @Test
    @Order(3)
    public void testRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(testRefreshToken);

        webTestClient.post()
                .uri("/api/v1/auth/refresh-token")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(refreshToken), RefreshToken.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.access_token").isNotEmpty()
                .jsonPath("$.refresh_token").isNotEmpty();
    }
}