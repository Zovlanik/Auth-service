package com.zovlanik.Auth_service.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Country {

    private Integer id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String name;
    private String alpha2;  //alpha2 и alpha3 - уникальные идентификаторы
    private String alpha3;
    private String status;

}
