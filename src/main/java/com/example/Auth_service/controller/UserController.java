package com.example.Auth_service.controller;

import com.example.Auth_service.entity.User;
import com.example.Auth_service.service.UserService;
import com.example.common.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public Mono<User> createUser(@RequestBody UserDto userDto, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        return userService.createUser(userDto, userToken);
    }
}
