package com.westflow.seeds_manager_api.domain.model;

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
    private Boolean active = true;

    @Builder
    public User(Long id, String email, String password, String name, String position,
                AccessLevel accessLevel, LocalDateTime createdAt,
                LocalDateTime updatedAt, LocalDateTime lastLogin, Boolean active) {

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
        if (active != null) {
            this.active = active;
        }
    }

    public User withEncodedPassword(String encodedPassword) {
        return new User(
                this.id,
                this.email,
                encodedPassword,
                this.name,
                this.position,
                this.accessLevel,
                this.createdAt,
                this.updatedAt,
                this.lastLogin,
                this.active
        );
    }

    public void markLogin() {
        this.lastLogin = LocalDateTime.now();
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

    public void deactivate() {
        if (!this.active) {
            throw new ValidationException("Usuário já está deletada");
        }
        this.active = false;
    }

    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }
}
