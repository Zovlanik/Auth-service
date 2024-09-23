package com.zovlanik.Auth_service.service;


import com.zovlanik.Auth_service.entity.Individual;
import com.example.common.IndividualDto;
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

    @Value("${app.constant.person_service.server_url}")
    private String SERVER_URL;

    private final AuthService authService;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(SERVER_URL)
                .build();
    }

    public Mono<Individual> createIndividual(IndividualDto individualDto, String userToken){

        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if(isValid){
                        Mono<Individual> individual = webClient.post()
                                .uri("/api/v1/individual")
                                .bodyValue(individualDto)
                                .retrieve()
                                .bodyToMono(Individual.class);
                        return individual;
                    }else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }

    public Mono<Individual> updateIndividual(UUID id, IndividualDto individualDto, String userToken){

        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if(isValid){
                        Mono<Individual> individual = webClient.put()
                                .uri("/api/v1/individual/" + id)
                                .bodyValue(individualDto)
                                .retrieve()
                                .bodyToMono(Individual.class);
                        return individual;
                    }else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }


    public Mono<Individual> getIndividual(UUID uuid, String userToken) {
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if(isValid){
                        Mono<Individual> individual = webClient.get()
                                .uri("/api/v1/individual/" + uuid)
                                .retrieve()
                                .bodyToMono(Individual.class);
                        return individual;
                    }else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }


    public Mono<Individual> deleteById(UUID uuid, String userToken) {
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if(isValid){
                        Mono<Individual> individual = webClient.delete()
                                .uri("/api/v1/individual/" + uuid)
                                .retrieve()
                                .bodyToMono(Individual.class);
                        return individual;
                    }else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }

}
