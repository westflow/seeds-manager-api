package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class Seed {
    private Long id;
    private String species;
    private String cultivar;

    public Seed(Long id, String species, String cultivar) {
        if (species == null || cultivar == null) {
            throw new ValidationException("Species and cultivar must be provided");
        }
        this.id = id;
        this.species = species;
        this.cultivar = cultivar;
    }

}
