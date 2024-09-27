package com.zovlanik.Auth_service;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class KeycloakTestContainers {

    @Value("${app.constant.keycloak.client_secret}")
    static String CLIENT_SECRET;
    static final KeycloakContainer keycloakContainer;

    static {
        keycloakContainer = new KeycloakContainer()
                .withRealmImportFile("keycloak/person-service.json");
        keycloakContainer.start();
    }

    @DynamicPropertySource
    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloakContainer.getAuthServerUrl() + "/realms/MyTestService-realm");
        registry.add("app.constant.keycloak.server-url", () -> keycloakContainer.getAuthServerUrl());
        registry.add("app.constant.keycloak.client_secret", () -> CLIENT_SECRET);
    }
}
