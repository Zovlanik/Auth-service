package com.example.Auth_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("merchants")
//это компания, она не будет заходить в систему.
public class Merchant implements Persistable<UUID> {

    @Id
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

    @Override
    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }
    @Override
    public UUID getId() {
        return id;
    }
}
