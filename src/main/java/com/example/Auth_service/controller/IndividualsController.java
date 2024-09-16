package com.example.Auth_service.controller;

import com.example.Auth_service.entity.Individual;
import com.example.Auth_service.service.IndividualsService;
import com.example.Auth_service.utils.UUIDValidator;
import com.example.common.IndividualDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/individual")
public class IndividualsController {

    private final IndividualsService individualsService;

    @PostMapping
    public Mono<Individual> createIndividual(@RequestBody IndividualDto individualDto, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        return individualsService.createIndividual(individualDto, userToken);
    }

    @PutMapping("/{id}")
    public Mono<Individual> updateIndividual(@PathVariable String id, @RequestBody IndividualDto individualDto, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        if (UUIDValidator.isValidUUID(id)) {
            return individualsService.updateIndividual(UUID.fromString(id), individualDto, userToken);
        }
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/{id}")
    public Mono<Individual> getIndividualById(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        if (UUIDValidator.isValidUUID(id)) {
            return individualsService.getIndividual(UUID.fromString(id), userToken);
        }
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public Mono<Individual> deleteIndividualById(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        if (UUIDValidator.isValidUUID(id)) {
            return individualsService.deleteById(UUID.fromString(id), userToken);
        }
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }


}
