package com.example.Auth_service.controller;


import com.example.Auth_service.dto.AuthDto;
import com.example.Auth_service.dto.RefreshTokenDto;
import com.example.Auth_service.dto.RegistrationDto;
import com.example.Auth_service.entity.Individual;
import com.example.Auth_service.service.AuthService;
import com.example.Auth_service.utils.UUIDValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public Mono<String> registration (@RequestBody RegistrationDto registrationDto) {
        return authService.registration(registrationDto);
    }

    @PostMapping("/authorization")
    public Mono<RefreshTokenDto> authorization (@RequestBody AuthDto authDto) {
        return authService.authorization(authDto);
    }

    @PostMapping("/refresh-token")
    public Mono<String> refreshToken (@RequestBody String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

    @GetMapping("/{id}")
    public Mono<Individual> getIndividual (@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        if (UUIDValidator.isValidUUID(id)) {
            Mono<Individual> individual = authService.getIndividual(UUID.fromString(id), userToken);
            return individual;
        }
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

}