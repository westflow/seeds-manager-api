package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.Seed;

public interface SeedRepository {
    Seed save(Seed seed);
}
