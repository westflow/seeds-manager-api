package com.westflow.seeds_manager_api.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seeds")
public class SeedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String species;
    private String cultivar;
}
