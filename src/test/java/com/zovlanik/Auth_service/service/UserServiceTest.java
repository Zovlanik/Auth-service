package com.zovlanik.Auth_service.service;

import com.example.common.UserDto;
import com.zovlanik.Auth_service.entity.User;
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

class UserServiceTest {

    @InjectMocks
    private UserService userService;

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

    private UserDto userDto;
    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        userDto = new UserDto();
        user = new User();

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
    }

    @Test
    void createUser_Success() {
        // Mock the WebClient interactions
        when(requestBodyUriSpec.uri(eq("/api/v1/user"))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any(UserDto.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(User.class)).thenReturn(Mono.just(user));

        // Call the service method
        Mono<User> result = userService.createUser(userDto);

        // Validate the response
        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();

        verify(requestBodyUriSpec, times(1)).uri(eq("/api/v1/user"));
    }
}