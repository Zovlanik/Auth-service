package com.zovlanik.Auth_service.controller;


import com.example.common.AddressDto;
import com.zovlanik.Auth_service.entity.Address;
import com.zovlanik.Auth_service.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
public class AddressRestControllerV1 {

    private final AddressService addressService;

    @PostMapping
    public Mono<Address> createAddress(@RequestBody AddressDto addressDto) {
        return addressService.createAddress(addressDto);
    }

    @GetMapping("/{id}")
    public Mono<Address> getAddress(@PathVariable String id) {
        return addressService.getAddress(UUID.fromString(id));
    }

    @PutMapping("/{id}")
    public Mono<Address> updateAddress(@PathVariable String id, @RequestBody AddressDto addressDto) {
        return addressService.updateAddress(UUID.fromString(id), addressDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAddress(@PathVariable String id) {
        return addressService.deleteAddress(UUID.fromString(id));
    }
}
