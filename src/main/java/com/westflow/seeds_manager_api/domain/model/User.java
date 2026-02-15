package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class User {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String position;
    private AccessLevel accessLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
    private Boolean active = true;

    public static User newUser(String email, String password, String name, String position, AccessLevel accessLevel) {
        validateStatic(email, password, name, accessLevel);
        User u = new User();
        u.email = email;
        u.password = password;
        u.name = name;
        u.position = position;
        u.accessLevel = accessLevel;
        u.createdAt = LocalDateTime.now();
        u.updatedAt = LocalDateTime.now();
        u.active = true;
        return u;
    }

    public static User restore(Long id, String email, String password, String name, String position,
                               AccessLevel accessLevel, LocalDateTime createdAt,
                               LocalDateTime updatedAt, LocalDateTime lastLogin, Boolean active) {
        return new User(
                id,
                email,
                password,
                name,
                position,
                accessLevel,
                createdAt,
                updatedAt,
                lastLogin,
                active
        );
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

    private static void validateStatic(String email, String password, String name, AccessLevel accessLevel) {
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

    public void updateProfile(String name, String position) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Nome é obrigatório");
        }

        this.name = name;
        this.position = position;
        this.updatedAt = LocalDateTime.now();
    }

    public void adminUpdate(String email, String name, String position, AccessLevel accessLevel) {
        if (this.accessLevel == AccessLevel.ADMIN) {
            throw new ValidationException("Não é permitido alterar usuário ADMIN");
        }

        if (email != null && email.isBlank()) {
            throw new ValidationException("Email é obrigatório quando informado");
        }

        if (name != null && name.isBlank()) {
            throw new ValidationException("Nome é obrigatório quando informado");
        }

        if (email != null) {
            this.email = email;
        }

        if (name != null) {
            this.name = name;
        }

        if (position != null) {
            this.position = position;
        }

        if (accessLevel != null) {
            this.accessLevel = accessLevel;
        }

        this.updatedAt = LocalDateTime.now();
    }
}
