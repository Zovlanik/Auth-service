package com.example.Auth_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("users")
public class User implements Persistable<UUID> {
    @Id
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
