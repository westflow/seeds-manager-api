package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {

    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String position;
    private final AccessLevel accessLevel;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

    @Builder
    public User(Long id, String email, String password, String name, String position,
                AccessLevel accessLevel, LocalDateTime createdAt,
                LocalDateTime updatedAt, LocalDateTime lastLogin) {

        validate(email, password, name, accessLevel);
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.position = position;
        this.accessLevel = accessLevel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLogin = lastLogin;
    }

    private void validate(String email, String password, String name, AccessLevel accessLevel) {
        if (email == null || email.isBlank()) {
            throw new ValidationException("Email é obrigatório");
        }

        if (password == null || password.isBlank()) {
            throw new ValidationException("Senha é obrigatória");
        }

        if (name == null || name.isBlank()) {
            throw new ValidationException("Nome é obrigatório");
        }

        if (accessLevel == null) {
            throw new ValidationException("Nível de acesso é obrigatório");
        }
    }

    public void markLogin() {
        this.lastLogin = LocalDateTime.now();
    }
}
