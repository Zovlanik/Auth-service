package com.zovlanik.Auth_service.service;

import com.example.common.IndividualDto;
import com.zovlanik.Auth_service.entity.Individual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class IndividualsServiceTest {

    @InjectMocks
    private IndividualsService individualService;

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

    private IndividualDto individualDto;
    private Individual individual;
    private UUID individualId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        individualId = UUID.randomUUID();
        individualDto = new IndividualDto();
        individual = new Individual();

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
    }

    @Test
    void createIndividual_Success() {
        // Mock the WebClient interactions
        when(requestBodyUriSpec.uri(eq("/api/v1/individual"))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any(IndividualDto.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Individual.class)).thenReturn(Mono.just(individual));

        // Call the service method
        Mono<Individual> result = individualService.createIndividual(individualDto);

        // Validate the response
        StepVerifier.create(result)
                .expectNext(individual)
                .verifyComplete();

        verify(requestBodyUriSpec, times(1)).uri(eq("/api/v1/individual"));
    }

    @Test
    void getIndividual_Success() {
        when(requestHeadersUriSpec.uri(eq("/api/v1/individual/" + individualId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Individual.class)).thenReturn(Mono.just(individual));

        Mono<Individual> result = individualService.getIndividual(individualId);

        StepVerifier.create(result)
                .expectNext(individual)
                .verifyComplete();

        verify(requestHeadersUriSpec, times(1)).uri(eq("/api/v1/individual/" + individualId));
    }

    @Test
    void updateIndividual_Success() {
        when(requestBodyUriSpec.uri(eq("/api/v1/individual/" + individualId))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any(IndividualDto.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Individual.class)).thenReturn(Mono.just(individual));

        Mono<Individual> result = individualService.updateIndividual(individualId, individualDto);

        StepVerifier.create(result)
                .expectNext(individual)
                .verifyComplete();

        verify(requestBodyUriSpec, times(1)).uri(eq("/api/v1/individual/" + individualId));
    }

    @Test
    void deleteIndividual_Success() {
        when(requestHeadersUriSpec.uri(eq("/api/v1/individual/" + individualId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Individual.class)).thenReturn(Mono.just(individual));

        Mono<Individual> result = individualService.deleteById(individualId);

        StepVerifier.create(result)
                .expectNext(individual)
                .verifyComplete();

        verify(requestHeadersUriSpec, times(1)).uri(eq("/api/v1/individual/" + individualId));
    }

}