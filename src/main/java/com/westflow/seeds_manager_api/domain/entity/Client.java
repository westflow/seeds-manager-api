package com.westflow.seeds_manager_api.domain.entity;

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
public class Client {
    private Long id;
    private String number;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
