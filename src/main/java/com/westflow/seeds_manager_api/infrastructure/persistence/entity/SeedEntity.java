package com.westflow.seeds_manager_api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seeds")
public class SeedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "species", nullable = false)
    private String species;
    @Column(name = "cultivar", nullable = false)
    private String cultivar;
    @Column(name = "normalized_species")
    private String normalizedSpecies;
    @Column(name = "normalized_cultivar", nullable = false)
    private String normalizedCultivar;
    private boolean isProtected;

    @Column(name = "active", nullable = false)
    private boolean active = true;
}
