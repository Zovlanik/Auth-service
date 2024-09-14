package com.example.Auth_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Individual {

    private UUID id;
    private UUID userId;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String passportNumber;
    private String phoneNumber;
    private String email;
    private LocalDateTime verifiedAt;
    private LocalDateTime archivedAt;
    private String status;


    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }

    public UUID getId() {
        return id;
    }
}
