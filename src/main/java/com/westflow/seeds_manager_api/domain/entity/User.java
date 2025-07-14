package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private final Long id;
    private final String username;
    private final String password;
    private final String name;
    private final String position;
    private final AccessLevel accessLevel;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

    public User(Long id, String username, String password, String name, String position,
                AccessLevel accessLevel, LocalDateTime createdAt,
                LocalDateTime updatedAt, LocalDateTime lastLogin) {

        if (username == null || password == null || accessLevel == null)
            throw new ValidationException("Username, password and access level are required");

        this.id = id;
        this.username = username;
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
