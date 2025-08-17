package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.Seed;

import java.util.Optional;

public interface SeedRepository {
    Seed save(Seed seed);
    Optional<Seed> findById(Long id);
}
