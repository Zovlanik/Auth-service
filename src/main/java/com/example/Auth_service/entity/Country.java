package com.example.Auth_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("countries")
public class Country implements Persistable<Integer> {
    @Id
    private Integer id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String name;
    private String alpha2;  //alpha2 и alpha3 - уникальные идентификаторы
    private String alpha3;
    private String status;

    @Override
    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }
    @Override
    public Integer getId() {
        return id;
    }
}
