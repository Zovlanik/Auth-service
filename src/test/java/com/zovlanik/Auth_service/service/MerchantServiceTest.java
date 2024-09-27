package com.zovlanik.Auth_service.service;

import com.example.common.MerchantDto;
import com.zovlanik.Auth_service.entity.Merchant;
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

class MerchantServiceTest {

    @InjectMocks
    private MerchantService merchantService;

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

    private MerchantDto merchantDto;
    private Merchant merchant;
    private UUID merchantId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        merchantId = UUID.randomUUID();
        merchantDto = new MerchantDto();
        merchant = new Merchant();

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
    }

    @Test
    void createMerchant_Success() {
        // Mock the WebClient interactions
        when(requestBodyUriSpec.uri(eq("/api/v1/merchant"))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any(MerchantDto.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Merchant.class)).thenReturn(Mono.just(merchant));

        // Call the service method
        Mono<Merchant> result = merchantService.createMerchant(merchantDto);

        // Validate the response
        StepVerifier.create(result)
                .expectNext(merchant)
                .verifyComplete();

        verify(requestBodyUriSpec, times(1)).uri(eq("/api/v1/merchant"));
    }

    @Test
    void getMerchant_Success() {
        when(requestHeadersUriSpec.uri(eq("/api/v1/merchant/" + merchantId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Merchant.class)).thenReturn(Mono.just(merchant));

        Mono<Merchant> result = merchantService.findById(merchantId);

        StepVerifier.create(result)
                .expectNext(merchant)
                .verifyComplete();

        verify(requestHeadersUriSpec, times(1)).uri(eq("/api/v1/merchant/" + merchantId));
    }

    @Test
    void updateMerchant_Success() {
        when(requestBodyUriSpec.uri(eq("/api/v1/merchant/" + merchantId))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any(MerchantDto.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Merchant.class)).thenReturn(Mono.just(merchant));

        Mono<Merchant> result = merchantService.updateMerchant(merchantId, merchantDto);

        StepVerifier.create(result)
                .expectNext(merchant)
                .verifyComplete();

        verify(requestBodyUriSpec, times(1)).uri(eq("/api/v1/merchant/" + merchantId));
    }

    @Test
    void deleteMerchant_Success() {
        when(requestHeadersUriSpec.uri(eq("/api/v1/merchant/" + merchantId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        Mono<Void> result = merchantService.deleteById(merchantId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(requestHeadersUriSpec, times(1)).uri(eq("/api/v1/merchant/" + merchantId));
    }
}