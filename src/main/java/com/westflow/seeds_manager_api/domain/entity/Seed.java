package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.*;

@Getter
public class Seed {

    private final Long id;
    private final String species;
    private final String cultivar;
    private final boolean isProtected;

    @Builder
    public Seed(Long id, String species, String cultivar, boolean isProtected) {
        validate(species, cultivar);
        this.id = id;
        this.species = species;
        this.cultivar = cultivar;
        this.isProtected = isProtected;
    }

    private void validate(String species, String cultivar) {
        if (species == null || species.isBlank()) {
            throw new ValidationException("Espécie é obrigatória");
        }

        if (cultivar == null || cultivar.isBlank()) {
            throw new ValidationException("Cultivar é obrigatória");
        }
    }
}
