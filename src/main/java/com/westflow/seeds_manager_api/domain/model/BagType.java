package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BagType {

    private Long id;
    private String name;
    private Boolean active;

    public static BagType newBagType(String name) {
        validate(name);

        BagType bagType = new BagType();
        bagType.id = null;
        bagType.name = name;
        bagType.active = true;
        return bagType;
    }

    public static BagType restore(Long id, String name, Boolean active) {
        validate(name);

        return new BagType(id, name, active);
    }

    public void update(String name) {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new BusinessException("Tipo de sacaria está inativo e não pode ser atualizado.");
        }

        validate(name);
        this.name = name;
    }

    public void deactivate() {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new BusinessException("Tipo de sacaria já está deletado.");
        }
        this.active = false;
    }

    private static void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Nome da sacaria é obrigatório");
        }
    }
}
