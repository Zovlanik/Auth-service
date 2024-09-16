package com.example.Auth_service.controller;


import com.example.Auth_service.entity.Country;
import com.example.Auth_service.service.CountryService;
import com.example.common.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/country")
public class CountryController {

    private final CountryService countryService;

    @PostMapping
    public Mono<Country> createCountry(@RequestBody CountryDto countryDto, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        return countryService.createCountry(countryDto, userToken);
    }

    @GetMapping("/{id}")
    public Mono<Country> getCountry(@PathVariable Integer id, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        return countryService.getCountry(id, userToken);
    }

    @PutMapping("/{id}")
    public Mono<Country> updateCountry(@PathVariable Integer id, @RequestBody CountryDto countryDto, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        return countryService.updateCountry(id, countryDto, userToken);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCountry(@PathVariable Integer id, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        return countryService.deleteCountry(id, userToken);
    }
}
