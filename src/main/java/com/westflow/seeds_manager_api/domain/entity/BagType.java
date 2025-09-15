package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BagType {
    private final Long id;
    private final String name;
    private boolean isActive = true;

    @Builder
    public BagType(Long id, String name) {
        validate(name);
        this.id = id;
        this.name = name;
    }

    private void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Nome da sacaria é obrigatório");
        }
    }

    public void deactivate() {
        if (!this.isActive) {
            throw new BusinessException("Tipo de sacaria já está deletado.");
        }
        this.isActive = false;
    }
}
