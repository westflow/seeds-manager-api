package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.enums.SystemRole;
import com.westflow.seeds_manager_api.domain.enums.TenantRole;
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
    private TenantRole tenantRole;
    private SystemRole systemRole;
    private Long companyId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
    private Boolean active = true;

    public static User newUser(String email, String password, String name, String position,
                               TenantRole tenantRole, SystemRole systemRole) {
        validateStatic(email, password, name, tenantRole);
        User u = new User();
        u.email = email;
        u.password = password;
        u.name = name;
        u.position = position;
        u.tenantRole = tenantRole;
        u.systemRole = systemRole;
        u.companyId = null;
        u.createdAt = LocalDateTime.now();
        u.updatedAt = LocalDateTime.now();
        u.active = true;
        return u;
    }

    public static User restore(Long id, String email, String password, String name, String position,
                               TenantRole tenantRole, SystemRole systemRole, Long companyId,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt, LocalDateTime lastLogin, Boolean active) {
        return new User(
                id,
                email,
                password,
                name,
                position,
                tenantRole,
                systemRole,
                companyId,
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
                this.tenantRole,
                this.systemRole,
                this.companyId,
                this.createdAt,
                this.updatedAt,
                this.lastLogin,
                this.active
        );
    }

    public User withCompanyId(Long companyId) {
        return new User(
                this.id,
                this.email,
                this.password,
                this.name,
                this.position,
                this.tenantRole,
                this.systemRole,
                companyId,
                this.createdAt,
                this.updatedAt,
                this.lastLogin,
                this.active
        );
    }

    public void markLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    private static void validateStatic(String email, String password, String name, TenantRole tenantRole) {
        if (email == null || email.isBlank()) {
            throw new ValidationException("Email é obrigatório");
        }

        if (password == null || password.isBlank()) {
            throw new ValidationException("Senha é obrigatória");
        }

        if (name == null || name.isBlank()) {
            throw new ValidationException("Nome é obrigatório");
        }

        if (tenantRole == null) {
            throw new ValidationException("Perfil é obrigatório");
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

    public void adminUpdate(String email, String name, String position, TenantRole tenantRole) {

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

        if (tenantRole != null) {
            this.tenantRole = tenantRole;
        }

        this.updatedAt = LocalDateTime.now();
    }
}
