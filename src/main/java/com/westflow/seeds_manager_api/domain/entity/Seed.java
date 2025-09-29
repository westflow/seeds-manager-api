package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.*;

@Getter
public class Seed {

    private final Long id;
    private String species;
    private String cultivar;
    private final boolean isProtected;
    private boolean isActive = true;

    private String normalizedSpecies;
    private String normalizedCultivar;

    @Builder
    public Seed(Long id, String species, String cultivar, boolean isProtected) {
        validate(species, cultivar);
        this.id = id;
        this.species = species;
        this.cultivar = cultivar;
        this.isProtected = isProtected;
        this.normalizedSpecies = normalize(species);
        this.normalizedCultivar = normalize(cultivar).toUpperCase();
    }

    private void validate(String species, String cultivar) {
        if (species == null || species.isBlank()) {
            throw new ValidationException("Espécie é obrigatória");
        }
        if (cultivar == null || cultivar.isBlank()) {
            throw new ValidationException("Cultivar é obrigatória");
        }
    }

    private String normalize(String input) {
        return java.text.Normalizer.normalize(input.trim(), java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}", "")
                .toLowerCase();
    }

    public void deactivate() {
        if (!isActive) {
            throw new ValidationException("A semente já está deletada.");
        }
        this.isActive = false;
    }
    
    public void update(Seed updatedSeed) {
        if (updatedSeed == null) {
            throw new ValidationException("Dados de atualização inválidos");
        }
        
        if (updatedSeed.getSpecies() != null) {
            this.species = updatedSeed.getSpecies();
        }
        
        if (updatedSeed.getCultivar() != null) {
            this.cultivar = updatedSeed.getCultivar();
        }
        
        // Atualiza os campos normalizados
        if (this.species != null) {
            this.normalizedSpecies = normalize(this.species);
        }
        
        if (this.cultivar != null) {
            this.normalizedCultivar = normalize(this.cultivar).toUpperCase();
        }
    }
}
