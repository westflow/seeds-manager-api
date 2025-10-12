package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Lab {
    private final Long id;
    private final String name;
    private final String state;
    private final String renasemCode;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private Boolean active = true;

    @Builder
    public Lab(Long id, String name, String state, String renasemCode,
               LocalDateTime createdAt, LocalDateTime updatedAt, Boolean active) {
        validate(name, state, renasemCode);
        this.id = id;
        this.name = name;
        this.state = state;
        this.renasemCode = renasemCode;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt;
        if (active != null) {
            this.active = active;
        }
    }

    private void validate(String name, String state, String renasemCode) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Nome do laboratório é obrigatório");
        }
        if (state == null || state.isBlank()) {
            throw new ValidationException("Estado do laboratório é obrigatório");
        }
        if (renasemCode == null || renasemCode.isBlank()) {
            throw new ValidationException("Código RENASEM é obrigatório");
        }
    }

    public void deactivate() {
        if (!this.active) {
            throw new ValidationException("Laboratório já está inativo.");
        }
        this.active = false;
    }
}
