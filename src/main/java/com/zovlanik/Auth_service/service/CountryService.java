package com.zovlanik.Auth_service.service;


import com.zovlanik.Auth_service.entity.Country;
import com.example.common.CountryDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CountryService {

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
