package com.zovlanik.Auth_service.service;


import com.zovlanik.Auth_service.entity.User;
import com.example.common.UserDto;
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

    private final AuthService authService;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(SERVER_URL)
                .build();
    }

    public Mono<User> createUser(UserDto userDto, String userToken) {
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if (isValid) {
                        Mono<User> userMono = webClient.post()
                                .uri("/api/v1/user")
                                .bodyValue(userDto)
                                .retrieve()
                                .bodyToMono(User.class);
                        return userMono;
                    } else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }
}
