package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Getter;

@Getter
public class Seed {
    private final Long id;
    private final String species;
    private final String cultivar;

    public Seed(Long id, String species, String cultivar) {
        if (species == null || cultivar == null) {
            throw new ValidationException("Species and cultivar must be provided");
        }
        this.id = id;
        this.species = species;
        this.cultivar = cultivar;
    }

}
