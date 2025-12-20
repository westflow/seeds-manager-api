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
public class Lab {

    private Long id;
    private String name;
    private String state;
    private String renasemCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;

    public static Lab newLab(String name, String state, String renasemCode) {
        validate(name, state, renasemCode);

        Lab lab = new Lab();
        lab.id = null;
        lab.name = name;
        lab.state = state;
        lab.renasemCode = renasemCode;
        lab.createdAt = LocalDateTime.now();
        lab.updatedAt = null;
        lab.active = true;
        return lab;
    }

    public static Lab restore(Long id,
                              String name,
                              String state,
                              String renasemCode,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt,
                              Boolean active) {
        validate(name, state, renasemCode);

        return new Lab(
                id,
                name,
                state,
                renasemCode,
                createdAt,
                updatedAt,
                active
        );
    }

    public void update(String name, String state, String renasemCode) {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new BusinessException("Laboratório está inativo e não pode ser atualizado.");
        }

        validate(name, state, renasemCode);
        this.name = name;
        this.state = state;
        this.renasemCode = renasemCode;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new ValidationException("Laboratório já está inativo.");
        }
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    private static void validate(String name, String state, String renasemCode) {
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
}
