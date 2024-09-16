package com.example.Auth_service.service;

import com.example.Auth_service.dto.AuthDto;
import com.example.Auth_service.dto.RefreshTokenDto;
import com.example.Auth_service.dto.RegistrationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String SERVER_URL = "http://localhost:8080";
    private static final String REALM = "MyTestService-realm";
    private static final String CLIENT_ID = "person-service";
    private static final String CLIENT_SECRET = "m3ljv2jj5B1mwlN3nq3P9jvD5OInTvFC";

    private final WebClient webClientKeyCloak = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Mono<String> registration(RegistrationDto registrationDto) {

        // регистрируем новых пользователей под админом, которого заранее создали на сервере
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .username("realmadmin")
                .password("realmadmin")
                .grantType("password")
                .build();

        RealmResource realmResource = keycloak.realm("MyTestService-realm");


        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(registrationDto.getEmail());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(registrationDto.getUsername());
        userRepresentation.setFirstName(registrationDto.getFirstName());
        userRepresentation.setLastName(registrationDto.getLastName());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(registrationDto.getPassword());
        userRepresentation.setCredentials(Collections.singletonList(credential));


        Response response = realmResource.users().create(userRepresentation);

        if (response.getStatus() == 201) {
            return Mono.just("User created successfully");
        } else {
            return Mono.just("Failed to create user: " + response.getStatusInfo().toString());
        }

    }

    public Mono<RefreshTokenDto> authorization(AuthDto authDto) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .username(authDto.getUsername())
                .password(authDto.getPassword())
                .grantType("password")
                .build();

        // Получение токена
        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto();
        refreshTokenDto.setAccessToken(tokenResponse.getToken());
        refreshTokenDto.setRefreshToken(tokenResponse.getRefreshToken());
        return Mono.just(refreshTokenDto);
    }

    public Mono<String> refreshToken(String refreshToken) {

        Mono<String> stringMono = webClientKeyCloak.post()
                .uri("/realms/" + REALM + "/protocol/openid-connect/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(
                        "client_id=" + CLIENT_ID +
                        "&client_secret=" + CLIENT_SECRET +
                        "&grant_type=refresh_token" +
                        "&refresh_token=" + refreshToken
                )
                .retrieve()
                .bodyToMono(String.class);

        return stringMono;
    }



/*
    // рабочая версия через два рест запроса в кейклоак


    private final WebClient webClientKeyCloak = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

    public Mono<Void> registration (AuthDto authDto){
        return getAdminToken()
                .flatMap(this::createUser);

    }


    // Получение токена администратора
    public Mono<String> getAdminToken() {
        return webClientKeyCloak.post()
                .uri("/realms/MyTestService-realm/protocol/openid-connect/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("client_id=person-service&client_secret=m3ljv2jj5B1mwlN3nq3P9jvD5OInTvFC&grant_type=password&username=admin&password=admin")
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"));
    }

    // Создание пользователя
    public Mono<Void> createUser(String token) {
        Map<String, Object> userRequest = new HashMap<>();
        userRequest.put("username", "newuser");
        userRequest.put("enabled", true);
        userRequest.put("firstName", "John");
        userRequest.put("lastName", "Doe");
        userRequest.put("email", "john.doe@example.com");

        return webClientKeyCloak.post()
                .uri("/admin/realms/MyTestService-realm/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .retrieve()
                .bodyToMono(Void.class);
    }

*/


    public Mono<Boolean> validateToken(String token) {

        Mono<String> responseMono = webClientKeyCloak.post()
                .uri("/realms/MyTestService-realm/protocol/openid-connect/token/introspect")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(
                        "client_id=" + CLIENT_ID +
                                "&client_secret=" + CLIENT_SECRET +
                                "&token=" + token
                )
                .retrieve()
                .bodyToMono(String.class);

        return responseMono.flatMap(response -> {
            try {
                JsonNode jsonNode = objectMapper.readTree(response);
                if (jsonNode.has("active")) {
                    return Mono.just(jsonNode.get("active").asBoolean());
                } else {
                    return Mono.just(false);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                // В случае ошибки парсинга возвращаем Mono с false
                return Mono.just(false);
            }
        });

    }
}
