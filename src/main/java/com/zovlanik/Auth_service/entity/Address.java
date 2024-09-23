package com.zovlanik.Auth_service.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Address{

    private UUID id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer countryId;
    private String address;
    private String zipCode;
    private LocalDateTime archived;
    private String city;
    private String state;

}
