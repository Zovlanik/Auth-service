package com.zovlanik.Auth_service.service;


import com.zovlanik.Auth_service.entity.Merchant;
import com.example.common.MerchantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final AuthService authService;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8084")
            .build();

    public Mono<Merchant> createMerchant(MerchantDto merchantDto, String userToken) {
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if (isValid) {
                        Mono<Merchant> merchantMono = webClient.post()
                                .uri("/api/v1/merchant")
                                .bodyValue(merchantDto)
                                .retrieve()
                                .bodyToMono(Merchant.class);
                        return merchantMono;
                    } else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }

    public Mono<Merchant> findById(UUID uuid, String userToken) {
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if (isValid) {
                        Mono<Merchant> merchantMono = webClient.get()
                                .uri("/api/v1/merchant/" + uuid)
                                .retrieve()
                                .bodyToMono(Merchant.class);
                        return merchantMono;
                    } else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }

    public Mono<Merchant> updateMerchant(UUID id, MerchantDto merchantDto, String userToken) {
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if (isValid) {
                        Mono<Merchant> merchantMono = webClient.put()
                                .uri("/api/v1/merchant/" + id)
                                .bodyValue(merchantDto)
                                .retrieve()
                                .bodyToMono(Merchant.class);
                        return merchantMono;
                    } else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }

    public Mono<Void> deleteById(UUID id, String userToken) {
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if (isValid) {
                        Mono<Void> voidMono = webClient.delete()
                                .uri("/api/v1/merchant/" + id)
                                .retrieve()
                                .bodyToMono(Void.class);
                        return voidMono;
                    } else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }
}
