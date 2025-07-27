package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
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

    public User(Long id, String email, String password, String name, String position,
                AccessLevel accessLevel, LocalDateTime createdAt,
                LocalDateTime updatedAt, LocalDateTime lastLogin) {

        if (email == null || password == null || accessLevel == null)
            throw new ValidationException("Email, password and access level are required");

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

    public void markLogin() {
        this.lastLogin = LocalDateTime.now();
    }
}
