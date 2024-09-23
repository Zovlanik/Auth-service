package com.zovlanik.Auth_service.controller;

import com.zovlanik.Auth_service.entity.Merchant;
import com.zovlanik.Auth_service.service.MerchantService;
import com.zovlanik.Auth_service.utils.UUIDValidator;
import com.example.common.MerchantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant")
public class MerchantRestControllerV1 {

    private final MerchantService merchantService;

    @PostMapping
    public Mono<Merchant> createMerchant(@RequestBody MerchantDto merchantDto, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        return merchantService.createMerchant(merchantDto, userToken);
    }

    @PutMapping("/{id}")
    public Mono<Merchant> updateMerchant(@PathVariable String id, @RequestBody MerchantDto merchantDto, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        if (UUIDValidator.isValidUUID(id)) {
            return merchantService.updateMerchant(UUID.fromString(id), merchantDto, userToken);
        }
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/{id}")
    public Mono<Merchant> getMerchantById(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        if (UUIDValidator.isValidUUID(id)) {
            return merchantService.findById(UUID.fromString(id), userToken);
        }
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteMerchantById(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        String userToken = authHeader.replace("Bearer ", "");
        if (UUIDValidator.isValidUUID(id)) {
            return merchantService.deleteById(UUID.fromString(id), userToken);
        }
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
}
