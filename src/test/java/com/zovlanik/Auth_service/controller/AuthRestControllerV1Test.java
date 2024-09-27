package com.zovlanik.Auth_service.controller;

import com.example.common.AddressDto;
import com.example.common.IndividualDto;
import com.example.common.UserDto;
import com.zovlanik.Auth_service.KeycloakTestContainers;
import com.zovlanik.Auth_service.dto.AuthDto;
import com.zovlanik.Auth_service.dto.KeyCloakUserDto;
import com.zovlanik.Auth_service.dto.RegistrationDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.zovlanik.Auth_service.utils.MockData.getRegistrationDto;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthRestControllerV1Test extends KeycloakTestContainers{

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    public void testRegistration() {
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
        System.out.println("Please, WAIT!");
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
                .expectBody()
                .jsonPath("$.access_token").isNotEmpty()
                .jsonPath("$.refresh_token").isNotEmpty();
    }
}