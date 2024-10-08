package com.zovlanik.Auth_service.controller;


import com.zovlanik.Auth_service.dto.AccessTokenDto;
import com.zovlanik.Auth_service.dto.AuthDto;
import com.zovlanik.Auth_service.dto.RefreshToken;
import com.zovlanik.Auth_service.dto.RegistrationDto;
import com.zovlanik.Auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestControllerV1 {

    private final AuthService authService;

    @PostMapping("/registration")
    public Mono<AccessTokenDto> registration(@RequestBody RegistrationDto registrationDto) {
        return authService.registration(registrationDto);
    }

    @PostMapping("/authorization")
    public Mono<AccessTokenDto> authorization(@RequestBody AuthDto authDto) {
        return authService.authorization(authDto);
    }

    @PostMapping("/refresh-token")
    public Mono<AccessTokenDto> refreshToken(@RequestBody RefreshToken refreshToken) {
        return authService.refreshToken(refreshToken);
    }

}