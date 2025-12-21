package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seed {

    private Long id;
    private String species;
    private String cultivar;
    private boolean isProtected;
    private Boolean active = true;

    private String normalizedSpecies;
    private String normalizedCultivar;

    public static Seed newSeed(String species, String cultivar, boolean isProtected) {
        validateStatic(species, cultivar);
        Seed seed = new Seed();
        seed.id = null;
        seed.species = species;
        seed.cultivar = cultivar;
        seed.isProtected = isProtected;
        seed.normalizedSpecies = seed.normalize(species);
        seed.normalizedCultivar = seed.normalize(cultivar).toUpperCase();
        seed.active = true;
        return seed;
    }

    public static Seed restore(Long id, String species, String cultivar, boolean isProtected, String normalizedSpecies, String normalizedCultivar, Boolean active) {
        Seed seed = new Seed();
        seed.id = id;
        seed.species = species;
        seed.cultivar = cultivar;
        seed.isProtected = isProtected;
        seed.normalizedSpecies = normalizedSpecies;
        seed.normalizedCultivar = normalizedCultivar;
        seed.active = active != null ? active : true;
        return seed;
    }

    private static void validateStatic(String species, String cultivar) {
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
        if (!active) {
            throw new ValidationException("A semente já está deletada.");
        }
        this.active = false;
    }
    
    public void update(String species, String cultivar, boolean isProtected) {
        if ((species == null || species.isBlank()) && (cultivar == null || cultivar.isBlank())) {
            throw new ValidationException("Dados de atualização inválidos");
        }
        if (species != null) this.species = species;
        if (cultivar != null) this.cultivar = cultivar;
        this.isProtected = isProtected;
        if (this.species != null) {
            this.normalizedSpecies = normalize(this.species);
        }
        if (this.cultivar != null) {
            this.normalizedCultivar = normalize(this.cultivar).toUpperCase();
        }
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
        this.isProtected = updatedSeed.isProtected();
        if (this.species != null) {
            this.normalizedSpecies = normalize(this.species);
        }
        if (this.cultivar != null) {
            this.normalizedCultivar = normalize(this.cultivar).toUpperCase();
        }
    }
}
