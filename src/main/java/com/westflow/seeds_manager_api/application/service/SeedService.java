package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.Seed;

import java.util.Optional;

public interface SeedService {
    Seed register(Seed seed);
    Optional<Seed> findById(Long id);
}
