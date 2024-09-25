package com.zovlanik.Auth_service.service;

import com.example.common.UserDto;
import com.zovlanik.Auth_service.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${app.constant.person_service.server_url}")
    private String SERVER_URL;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(SERVER_URL)
                .build();
    }

    public Mono<User> createUser(UserDto userDto) {
        return webClient.post()
                .uri("/api/v1/user")
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(User.class);
    }
}
