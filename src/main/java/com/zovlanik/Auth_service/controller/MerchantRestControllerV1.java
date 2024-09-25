package com.zovlanik.Auth_service.controller;

import com.example.common.MerchantDto;
import com.zovlanik.Auth_service.entity.Merchant;
import com.zovlanik.Auth_service.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant")
public class MerchantRestControllerV1 {

    private final MerchantService merchantService;

    @PostMapping
    public Mono<Merchant> createMerchant(@RequestBody MerchantDto merchantDto) {
        return merchantService.createMerchant(merchantDto);
    }

    @PutMapping("/{id}")
    public Mono<Merchant> updateMerchant(@PathVariable String id, @RequestBody MerchantDto merchantDto) {
        return merchantService.updateMerchant(UUID.fromString(id), merchantDto);
    }

    @GetMapping("/{id}")
    public Mono<Merchant> getMerchantById(@PathVariable String id) {
        return merchantService.findById(UUID.fromString(id));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteMerchantById(@PathVariable String id) {
        return merchantService.deleteById(UUID.fromString(id));

    }
}
