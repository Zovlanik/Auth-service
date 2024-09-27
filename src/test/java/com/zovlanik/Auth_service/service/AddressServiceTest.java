package com.zovlanik.Auth_service.service;

import com.example.common.AddressDto;
import com.zovlanik.Auth_service.entity.Address;
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

class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

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

    private AddressDto addressDto;
    private Address address;
    private UUID addressId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addressId = UUID.randomUUID();
        addressDto = new AddressDto();
        address = new Address();

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
    }

    @Test
    void createAddress_Success() {
        // Mock the WebClient interactions
        when(requestBodyUriSpec.uri(eq("/api/v1/address"))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any(AddressDto.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Address.class)).thenReturn(Mono.just(address));

        // Call the service method
        Mono<Address> result = addressService.createAddress(addressDto);

        // Validate the response
        StepVerifier.create(result)
                .expectNext(address)
                .verifyComplete();

        verify(requestBodyUriSpec, times(1)).uri(eq("/api/v1/address"));
    }

    @Test
    void getAddress_Success() {
        when(requestHeadersUriSpec.uri(eq("/api/v1/address/" + addressId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Address.class)).thenReturn(Mono.just(address));

        Mono<Address> result = addressService.getAddress(addressId);

        StepVerifier.create(result)
                .expectNext(address)
                .verifyComplete();

        verify(requestHeadersUriSpec, times(1)).uri(eq("/api/v1/address/" + addressId));
    }

    @Test
    void updateAddress_Success() {
        when(requestBodyUriSpec.uri(eq("/api/v1/address/" + addressId))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any(AddressDto.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Address.class)).thenReturn(Mono.just(address));

        Mono<Address> result = addressService.updateAddress(addressId, addressDto);

        StepVerifier.create(result)
                .expectNext(address)
                .verifyComplete();

        verify(requestBodyUriSpec, times(1)).uri(eq("/api/v1/address/" + addressId));
    }

    @Test
    void deleteAddress_Success() {
        when(requestHeadersUriSpec.uri(eq("/api/v1/address/" + addressId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        Mono<Void> result = addressService.deleteAddress(addressId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(requestHeadersUriSpec, times(1)).uri(eq("/api/v1/address/" + addressId));
    }
}