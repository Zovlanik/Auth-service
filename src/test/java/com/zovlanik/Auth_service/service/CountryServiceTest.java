package com.zovlanik.Auth_service.service;

import com.example.common.CountryDto;
import com.zovlanik.Auth_service.entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CountryServiceTest {


    @InjectMocks
    private CountryService countryService;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Value("${app.constant.person_service.server-url}")
    private String serverUrl;

    private CountryDto countryDto;
    private Country country;
    private Integer countryId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryId = 2;
        countryDto = new CountryDto();
        country = new Country();

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
    }

    @Test
    void createCountry_Success() {
        // Mock the WebClient interactions
        when(requestBodyUriSpec.uri(eq("/api/v1/country"))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any(CountryDto.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Country.class)).thenReturn(Mono.just(country));

        // Call the service method
        Mono<Country> result = countryService.createCountry(countryDto);

        // Validate the response
        StepVerifier.create(result)
                .expectNext(country)
                .verifyComplete();

        verify(requestBodyUriSpec, times(1)).uri(eq("/api/v1/country"));
    }

    @Test
    void getCountry_Success() {
        when(requestHeadersUriSpec.uri(eq("/api/v1/country/" + countryId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Country.class)).thenReturn(Mono.just(country));

        Mono<Country> result = countryService.getCountry(countryId);

        StepVerifier.create(result)
                .expectNext(country)
                .verifyComplete();

        verify(requestHeadersUriSpec, times(1)).uri(eq("/api/v1/country/" + countryId));
    }

    @Test
    void updateCountry_Success() {
        when(requestBodyUriSpec.uri(eq("/api/v1/country/" + countryId))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any(CountryDto.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Country.class)).thenReturn(Mono.just(country));

        Mono<Country> result = countryService.updateCountry(countryId, countryDto);

        StepVerifier.create(result)
                .expectNext(country)
                .verifyComplete();

        verify(requestBodyUriSpec, times(1)).uri(eq("/api/v1/country/" + countryId));
    }

    @Test
    void deleteCountry_Success() {
        when(requestHeadersUriSpec.uri(eq("/api/v1/country/" + countryId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        Mono<Void> result = countryService.deleteCountry(countryId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(requestHeadersUriSpec, times(1)).uri(eq("/api/v1/country/" + countryId));
    }
}