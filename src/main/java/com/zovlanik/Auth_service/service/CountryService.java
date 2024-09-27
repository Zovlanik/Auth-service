package com.zovlanik.Auth_service.service;

import com.example.common.CountryDto;
import com.zovlanik.Auth_service.entity.Country;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CountryService {

    @Value("${app.constant.person_service.server-url}")
    private String SERVER_URL;
    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(SERVER_URL)
                .build();
    }

    public Mono<Country> createCountry(CountryDto countryDto) {
        return webClient.post()
                .uri("/api/v1/country")
                .bodyValue(countryDto)
                .retrieve()
                .bodyToMono(Country.class);
    }

    public Mono<Country> getCountry(Integer id) {
        return webClient.get()
                .uri("/api/v1/country/" + id)
                .retrieve()
                .bodyToMono(Country.class);
    }

    public Mono<Country> updateCountry(Integer id, CountryDto countryDto) {
        return webClient.put()
                .uri("/api/v1/country/" + id)
                .bodyValue(countryDto)
                .retrieve()
                .bodyToMono(Country.class);
    }

    public Mono<Void> deleteCountry(Integer id) {
        return webClient.delete()
                .uri("/api/v1/country/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
