package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.SeedService;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import org.springframework.stereotype.Service;

@Service
public class SeedServiceImpl implements SeedService {

    private final SeedRepository seedRepository;

    public SeedServiceImpl(SeedRepository seedRepository) {
        this.seedRepository = seedRepository;
    }

    @Override
    public Seed register(Seed seed) {
        return seedRepository.save(seed);
    }
}
