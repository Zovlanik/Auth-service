package com.example.Auth_service.service;


import com.example.Auth_service.entity.Individual;
import com.example.common.IndividualDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IndividualsService {

    private final AuthService authService;


    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8084")
            .build();

    public Mono<Individual> createIndividual(IndividualDto individualDto, String userToken){

        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if(isValid){
                        Mono<Individual> individualDtoMono = webClient.post()
                                .uri("/api/v1/individual")
                                .bodyValue(individualDto)
                                .retrieve()
                                .bodyToMono(Individual.class);
                        return individualDtoMono;
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
                        Mono<Individual> individualDtoMono = webClient.put()
                                .uri("/api/v1/individual/" + id)
                                .bodyValue(individualDto)
                                .retrieve()
                                .bodyToMono(Individual.class);
                        return individualDtoMono;
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
                        Mono<Individual> individualDtoMono = webClient.get()
                                .uri("/api/v1/individual/" + uuid)
                                .retrieve()
                                .bodyToMono(Individual.class);
                        return individualDtoMono;
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
                        Mono<Individual> individualDtoMono = webClient.delete()
                                .uri("/api/v1/individual/" + uuid)
                                .retrieve()
                                .bodyToMono(Individual.class);
                        return individualDtoMono;
                    }else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }

}
