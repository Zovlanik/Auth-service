package com.zovlanik.Auth_service.controller;


import com.zovlanik.Auth_service.entity.Address;
import com.zovlanik.Auth_service.service.AddressService;
import com.zovlanik.Auth_service.utils.UUIDValidator;
import com.example.common.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
public class AddressRestControllerV1 {

    private final AddressService addressService;

    @PostMapping
    public Mono<Address> createAddress(@RequestBody AddressDto addressDto, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        return addressService.createAddress(addressDto,userToken);
    }

    @GetMapping("/{id}")
    public Mono<Address> getAddress(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        if (UUIDValidator.isValidUUID(id)) {
            return addressService.getAddress(UUID.fromString(id),userToken);
        }
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/{id}")
    public Mono<Address> updateAddress(@PathVariable String id,@RequestBody AddressDto addressDto, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        if (UUIDValidator.isValidUUID(id)) {
            return addressService.updateAddress(UUID.fromString(id),addressDto,userToken);
        }
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAddress(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        if (UUIDValidator.isValidUUID(id)) {
            return addressService.deleteAddress(UUID.fromString(id),userToken);
        }
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
}
