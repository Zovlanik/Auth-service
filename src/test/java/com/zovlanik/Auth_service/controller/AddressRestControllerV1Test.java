package com.zovlanik.Auth_service.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AddressRestControllerV1Test {

    @Autowired
    WebTestClient webTestClient;



    @Test
    void createAddress() {
    }

    @Test
    void getAddress() {
    }

    @Test
    void updateAddress() {
    }

    @Test
    void deleteAddress() {
    }
}