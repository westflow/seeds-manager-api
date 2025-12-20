package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client {

    private Long id;
    private String number;
    private String name;
    private String email;
    private String phone;
    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Client newClient(
            String number,
            String name,
            String email,
            String phone
    ) {
        validate(number, name);

        Client client = new Client();
        client.id = null;
        client.number = number;
        client.name = name;
        client.email = email;
        client.phone = phone;
        client.active = true;
        client.createdAt = LocalDateTime.now();
        client.updatedAt = null;

        return client;
    }

    public static Client restore(
            Long id,
            String number,
            String name,
            String email,
            String phone,
            Boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        validate(number, name);

        return new Client(
                id,
                number,
                name,
                email,
                phone,
                active,
                createdAt,
                updatedAt
        );
    }

    public void update(
            String number,
            String name,
            String email,
            String phone
    ) {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new BusinessException("Cliente está inativo e não pode ser atualizado.");
        }

        validate(number, name);
        this.number = number;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new ValidationException("Cliente já está inativo.");
        }
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    private static void validate(String number, String name) {
        if (number == null || number.isBlank()) {
            throw new ValidationException("Número do cliente é obrigatório");
        }

        if (name == null || name.isBlank()) {
            throw new ValidationException("Nome do cliente é obrigatório");
        }
    }
}
