package com.zovlanik.Auth_service.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User {

    private UUID id;
    private String secretKey;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String firstName;
    private String lastName;
    private LocalDateTime verifiedAt;
    private LocalDateTime archivedAt;
    private String status;
    private boolean filled;
    private UUID addressId;
}
