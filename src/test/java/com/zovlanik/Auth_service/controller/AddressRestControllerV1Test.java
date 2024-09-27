package com.zovlanik.Auth_service.controller;

import com.example.common.AddressDto;
import com.zovlanik.Auth_service.KeycloakTestContainers;
import com.zovlanik.Auth_service.dto.AccessTokenDto;
import com.zovlanik.Auth_service.dto.KeyCloakUserDto;
import com.zovlanik.Auth_service.entity.Address;
import com.zovlanik.Auth_service.keycloak.KeycloakClient;
import com.zovlanik.Auth_service.service.AddressService;
import com.zovlanik.Auth_service.utils.MockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.zovlanik.Auth_service.utils.MockData.getAddressDto;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient

@Testcontainers
@ActiveProfiles("test")
class AddressRestControllerV1Test extends KeycloakTestContainers {

    @Autowired
    WebTestClient webTestClient;

    static AccessTokenDto accessTokenDto;

    @MockBean
    AddressService addressService;

    @BeforeAll
    static void init(@Autowired KeycloakClient keycloakClient) {
        KeyCloakUserDto keyCloakUserDto = KeyCloakUserDto.builder()
                .email("test2@mail.ru")
                .username("usertest2")
                .firstName("usertest2_first_name")
                .lastName("usertest2_last_name")
                .password("usertest2")
                .confirmPassword("usertest2")
                .build();
        accessTokenDto = keycloakClient.registration(keyCloakUserDto).block();
    }

    @Test
    void createAddress() {
        AddressDto addressDto = getAddressDto();
        Mockito.when(addressService.createAddress(any(AddressDto.class))).thenReturn(MockData.getAddress());

        WebTestClient.ResponseSpec exchange = webTestClient.post()
                .uri("/api/v1/address")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessTokenDto.getAccessToken())
                .body(Mono.just(addressDto), AddressDto.class)
                .exchange();
        exchange
                .expectStatus().isOk()
                .expectBody(Address.class)
                .value(address -> {
                    Assertions.assertEquals(addressDto.getAddress(), address.getAddress());
                    Assertions.assertEquals(addressDto.getCountryId(), address.getCountryId());
                    Assertions.assertEquals(addressDto.getCity(), address.getCity());
                    Assertions.assertEquals(addressDto.getZipCode(), address.getZipCode());
                });
    }

    @Test
    void getAddress() {
        AddressDto addressDto = getAddressDto();
        Mockito.when(addressService.getAddress(any(UUID.class))).thenReturn(MockData.getAddress());
        String id = "6a4d6102-f40a-4038-bbbd-cf5a61008bd3";
        webTestClient.get()
                .uri("/api/v1/address/" + id)
                .header("Authorization", "Bearer " + accessTokenDto.getAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Address.class)
                .value(address -> {
                    Assertions.assertEquals(addressDto.getAddress(), address.getAddress());
                    Assertions.assertEquals(addressDto.getCountryId(), address.getCountryId());
                    Assertions.assertEquals(addressDto.getCity(), address.getCity());
                    Assertions.assertEquals(addressDto.getZipCode(), address.getZipCode());
                });
    }

    @Test
    void updateAddress() {
        AddressDto addressDto = getAddressDto();
        Mockito.when(addressService.updateAddress(any(UUID.class), any(AddressDto.class))).thenReturn(MockData.getAddress());
        String id = "6a4d6102-f40a-4038-bbbd-cf5a61008bd3";
        webTestClient.put()
                .uri("/api/v1/address/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessTokenDto.getAccessToken())
                .body(Mono.just(addressDto), AddressDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Address.class)
                .value(address -> {
                    Assertions.assertEquals(addressDto.getAddress(), address.getAddress());
                    Assertions.assertEquals(addressDto.getCountryId(), address.getCountryId());
                    Assertions.assertEquals(addressDto.getCity(), address.getCity());
                    Assertions.assertEquals(addressDto.getZipCode(), address.getZipCode());
                });
    }

    @Test
    void deleteAddress() {
        Mockito.when(addressService.deleteAddress(any(UUID.class))).thenReturn(Mono.empty());
        String id = "6a4d6102-f40a-4038-bbbd-cf5a61008bd3";
        webTestClient.delete()
                .uri("/api/v1/address/" + id)
                .header("Authorization", "Bearer " + accessTokenDto.getAccessToken())
                .exchange()
                .expectStatus().isOk();
    }
}