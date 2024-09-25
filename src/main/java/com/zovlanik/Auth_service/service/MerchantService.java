package com.zovlanik.Auth_service.service;

import com.example.common.MerchantDto;
import com.zovlanik.Auth_service.entity.Merchant;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantService {

    @Value("${app.constant.person_service.server_url}")
    private String SERVER_URL;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(SERVER_URL)
                .build();
    }

    public Mono<Merchant> createMerchant(MerchantDto merchantDto) {
        return webClient.post()
                .uri("/api/v1/merchant")
                .bodyValue(merchantDto)
                .retrieve()
                .bodyToMono(Merchant.class);
    }

    public Mono<Merchant> findById(UUID uuid) {
        return webClient.get()
                .uri("/api/v1/merchant/" + uuid)
                .retrieve()
                .bodyToMono(Merchant.class);
    }

    public Mono<Merchant> updateMerchant(UUID id, MerchantDto merchantDto) {
        return webClient.put()
                .uri("/api/v1/merchant/" + id)
                .bodyValue(merchantDto)
                .retrieve()
                .bodyToMono(Merchant.class);
    }

    public Mono<Void> deleteById(UUID id) {
        return webClient.delete()
                .uri("/api/v1/merchant/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
