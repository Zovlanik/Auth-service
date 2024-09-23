package com.zovlanik.Auth_service.service;


import com.zovlanik.Auth_service.entity.Address;
import com.example.common.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AddressService {

    private final AuthService authService;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8084")
            .build();

    public Mono<Address> createAddress(AddressDto addressDto, String userToken){
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if(isValid){
                        Mono<Address>  addressMono = webClient.post()
                                .uri("/api/v1/address")
                                .bodyValue(addressDto)
                                .retrieve()
                                .bodyToMono(Address.class);
                        return addressMono;
                    }else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }

    public Mono<Address> getAddress(UUID uuid,String userToken){
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if(isValid){
                        Mono<Address> addressMono = webClient.get()
                                .uri("/api/v1/address/" + uuid)
                                .retrieve()
                                .bodyToMono(Address.class);
                        return addressMono;
                    }else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }

    public Mono<Address> updateAddress(UUID uuid, AddressDto addressDto,String userToken){
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if(isValid){
                        Mono<Address> addressMono = webClient.put()
                                .uri("/api/v1/address/" + uuid)
                                .bodyValue(addressDto)
                                .retrieve()
                                .bodyToMono(Address.class);
                        return addressMono;
                    }else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }

    public Mono<Void> deleteAddress(UUID uuid,String userToken){
        return authService.validateToken(userToken)
                .flatMap(isValid -> {
                    if(isValid){
                        Mono<Void> voidMono = webClient.delete()
                                .uri("/api/v1/individual/" + uuid)
                                .retrieve()
                                .bodyToMono(Void.class);
                        return voidMono;
                    }else {
                        // Если токен не валиден, возвращаем ошибку
                        return Mono.error(new RuntimeException("Invalid token"));// todo: сделать тут сообщение о неверном токене
                    }
                });
    }
}
