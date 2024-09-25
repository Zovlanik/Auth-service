package com.zovlanik.Auth_service.service;


import com.example.common.AddressDto;
import com.zovlanik.Auth_service.entity.Address;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AddressService {

    @Value("${app.constant.person_service.server_url}")
    private String SERVER_URL;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(SERVER_URL)
                .build();
    }

    public Mono<Address> createAddress(AddressDto addressDto) {
        return webClient.post()
                .uri("/api/v1/address")
                .bodyValue(addressDto)
                .retrieve()
                .bodyToMono(Address.class);
    }

    public Mono<Address> getAddress(UUID uuid) {
        return webClient.get()
                .uri("/api/v1/address/" + uuid)
                .retrieve()
                .bodyToMono(Address.class);
    }

    public Mono<Address> updateAddress(UUID uuid, AddressDto addressDto) {
        return webClient.put()
                .uri("/api/v1/address/" + uuid)
                .bodyValue(addressDto)
                .retrieve()
                .bodyToMono(Address.class);
    }

    public Mono<Void> deleteAddress(UUID uuid) {
        return webClient.delete()
                .uri("/api/v1/individual/" + uuid)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
