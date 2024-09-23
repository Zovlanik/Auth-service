package com.zovlanik.Auth_service.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
//это компания, она не будет заходить в систему.
public class Merchant {

    private UUID id;
    private UUID creatorId;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String companyName;
    private String companyId;
    private String email;
    private String phoneNumber;
    private LocalDateTime verifiedAt;
    private LocalDateTime archivedAt;
    private String status;
    private boolean filled;

}
