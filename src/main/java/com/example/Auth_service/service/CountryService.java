package com.example.Auth_service.service;


import com.example.Auth_service.entity.Country;
import com.example.common.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final AuthService authService;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8084")
            .build();

    public Mono<Country> createCountry(CountryDto countryDto, String userToken) {
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if (isValid) {
                        Mono<Country> countryMono = webClient.post()
                                .uri("/api/v1/country")
                                .bodyValue(countryDto)
                                .retrieve()
                                .bodyToMono(Country.class);
                        return countryMono;
                    } else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }

    public Mono<Country> getCountry(Integer id, String userToken) {
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if (isValid) {
                        Mono<Country> countryMono = webClient.get()
                                .uri("/api/v1/country/" + id)
                                .retrieve()
                                .bodyToMono(Country.class);
                        return countryMono;
                    } else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }

    public Mono<Country> updateCountry(Integer id, CountryDto countryDto, String userToken) {
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if (isValid) {
                        Mono<Country> countryMono = webClient.put()
                                .uri("/api/v1/country/" + id)
                                .bodyValue(countryDto)
                                .retrieve()
                                .bodyToMono(Country.class);
                        return countryMono;
                    } else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }

    public Mono<Void> deleteCountry(Integer id, String userToken) {
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if (isValid) {
                        Mono<Void> voidMono = webClient.delete()
                                .uri("/api/v1/country/" + id)
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
