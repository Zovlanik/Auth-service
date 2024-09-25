package com.zovlanik.Auth_service.controller;

import com.example.common.IndividualDto;
import com.zovlanik.Auth_service.entity.Individual;
import com.zovlanik.Auth_service.service.IndividualsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/individual")
public class IndividualsRestControllerV1 {

    private final IndividualsService individualsService;

    @PostMapping
    public Mono<Individual> createIndividual(@RequestBody IndividualDto individualDto) {
        return individualsService.createIndividual(individualDto);
    }

    @PutMapping("/{id}")
    public Mono<Individual> updateIndividual(@PathVariable String id, @RequestBody IndividualDto individualDto) {
        return individualsService.updateIndividual(UUID.fromString(id), individualDto);

    }

    @GetMapping("/{id}")
    public Mono<Individual> getIndividualById(@PathVariable String id) {
        return individualsService.getIndividual(UUID.fromString(id));
    }

    @DeleteMapping("/{id}")
    public Mono<Individual> deleteIndividualById(@PathVariable String id) {
        return individualsService.deleteById(UUID.fromString(id));
    }
}
