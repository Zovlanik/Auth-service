package com.zovlanik.Auth_service.service;

import com.example.common.IndividualDto;
import com.zovlanik.Auth_service.entity.Individual;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IndividualsService {

    @Value("${app.constant.person_service.server-url}")
    private String SERVER_URL;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(SERVER_URL)
                .build();
    }

    public Mono<Individual> createIndividual(IndividualDto individualDto) {
        return webClient.post()
                .uri("/api/v1/individual")
                .bodyValue(individualDto)
                .retrieve()
                .bodyToMono(Individual.class);
    }

    public Mono<Individual> updateIndividual(UUID id, IndividualDto individualDto) {

        return webClient.put()
                .uri("/api/v1/individual/" + id)
                .bodyValue(individualDto)
                .retrieve()
                .bodyToMono(Individual.class);
    }

    public Mono<Individual> getIndividual(UUID uuid) {
        return webClient.get()
                .uri("/api/v1/individual/" + uuid)
                .retrieve()
                .bodyToMono(Individual.class);
    }

    public Mono<Individual> deleteById(UUID uuid) {
        return webClient.delete()
                .uri("/api/v1/individual/" + uuid)
                .retrieve()
                .bodyToMono(Individual.class);
    }
}
