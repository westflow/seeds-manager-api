package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Builder;
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
    private boolean isActive = true;

    @Builder
    public Client(Long id, String number, String name, String email, String phone,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        validate(number, name);
        this.id = id;
        this.number = number;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void validate(String number, String name) {
        if (number == null || number.isBlank()) {
            throw new ValidationException("Número do cliente é obrigatório");
        }

        if (name == null || name.isBlank()) {
            throw new ValidationException("Nome do cliente é obrigatório");
        }
    }

    public void deactivate() {
        if (!this.isActive) {
            throw new ValidationException("Cliente já está inativo.");
        }
        this.isActive = false;
    }
}
