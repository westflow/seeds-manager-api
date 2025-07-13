package com.westflow.seeds_manager_api.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seeds")
public class Seed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String species;
    private String cultivar;
}
