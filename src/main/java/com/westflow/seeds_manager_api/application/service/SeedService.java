package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.Seed;

public interface SeedService {
    Seed register(Seed seed);
}
