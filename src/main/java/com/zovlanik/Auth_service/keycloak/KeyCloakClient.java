package com.zovlanik.Auth_service.keycloak;

import com.zovlanik.Auth_service.dto.AccessTokenDto;
import com.zovlanik.Auth_service.dto.KeyCloakUserDto;
import com.zovlanik.Auth_service.dto.KeycloakAuthToken;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class KeyCloakClient {

    private final Keycloak keycloak;

    @Value("${app.constant.keycloak.server-url}")
    private String SERVER_URL;
    @Value("${app.constant.keycloak.realm}")
    private String REALM;
    @Value("${app.constant.keycloak.client_id}")
    private String CLIENT_ID;
    @Value("${app.constant.keycloak.client_secret}")
    private String CLIENT_SECRET;

    @Value("${app.constant.keycloak.admin_username}")
    private String ADMIN_USERNAME;
    @Value("${app.constant.keycloak.admin_password}")
    private String ADMIN_PASSWORD;

    private WebClient webClientKeyCloak;

    @PostConstruct
    public void init() {
        this.webClientKeyCloak = WebClient.builder()
                .baseUrl(SERVER_URL)
                .build();
    }

    public Mono<AccessTokenDto> registration(KeyCloakUserDto keyCloakUserDto) {
        // регистрируем новых пользователей под админом, которого заранее создали на сервере
//        RealmResource realmResource = keycloak.realm(REALM);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(keyCloakUserDto.getEmail());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(keyCloakUserDto.getUsername());
        userRepresentation.setFirstName(keyCloakUserDto.getFirstName());
        userRepresentation.setLastName(keyCloakUserDto.getLastName());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(keyCloakUserDto.getPassword());
        userRepresentation.setCredentials(Collections.singletonList(credential));


        UsersResource usersResource = keycloak.realm(REALM).users();
        Response response = usersResource.create(userRepresentation);

        if (response.getStatus() == 201) {
            return authorization(keyCloakUserDto);
        } else {
            return Mono.just(AccessTokenDto.builder()
                    .accessToken("Failed to create user: " + response.getStatusInfo().toString())
                    .build());
        }
    }

    public Mono<AccessTokenDto> authorization(KeyCloakUserDto keyCloakUserDto) {
        // Получение токена
        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();

        return Mono.just(AccessTokenDto.builder()
                .accessToken(tokenResponse.getToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .build());
    }

    public Mono<AccessTokenDto> refreshToken(String refreshToken) {
        return webClientKeyCloak.post()
                .uri("/realms/" + REALM + "/protocol/openid-connect/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(
                        "client_id=" + CLIENT_ID +
                                "&client_secret=" + CLIENT_SECRET +
                                "&grant_type=refresh_token" +
                                "&refresh_token=" + refreshToken
                )
                .retrieve()
                .bodyToMono(KeycloakAuthToken.class)
                .flatMap(token -> {
                    AccessTokenDto accessTokenDto = AccessTokenDto.builder()
                            .accessToken(token.getAccessToken())
                            .refreshToken(token.getRefreshToken())
                            .build();
                    return Mono.just(accessTokenDto);
                });
    }
}
