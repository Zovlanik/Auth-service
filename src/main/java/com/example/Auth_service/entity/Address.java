package com.example.Auth_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("addresses")
public class Address implements Persistable<UUID> {
    @Id
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer countryId;
    private String address;
    private String zipCode;
    private LocalDateTime archived;
    private String city;
    private String state;

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
