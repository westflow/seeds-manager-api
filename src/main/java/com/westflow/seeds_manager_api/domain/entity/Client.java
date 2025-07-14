package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Client {
    private final Long id;
    private final String number;
    private final String name;
    private final String email;
    private final String phone;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Client(Long id, String number, String name, String email, String phone,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (number == null || name == null)
            throw new ValidationException("Client number and name must not be null");

        this.id = id;
        this.number = number;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
