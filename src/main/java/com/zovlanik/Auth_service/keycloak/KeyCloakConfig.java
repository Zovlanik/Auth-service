package com.zovlanik.Auth_service.keycloak;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyCloakConfig {
    @Value("${app.constant.keycloak.server-url}")
    private String SERVER_URL;
    @Value("${app.constant.keycloak.realm}")
    private String REALM;
    @Value("${app.constant.keycloak.client_id}")
    private String CLIENT_ID;
    @Value("${app.constant.keycloak.client_secret}")
    private String CLIENT_SECRET;

    @Value("${app.constant.keycloak.realm_admin_username}")
    private String REALM_ADMIN_USERNAME;
    @Value("${app.constant.keycloak.realm_admin_password}")
    private String REALM_ADMIN_PASSWORD;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .username(REALM_ADMIN_USERNAME)
                .password(REALM_ADMIN_PASSWORD)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }
}
