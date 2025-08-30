package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.SeedService;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeedServiceImpl implements SeedService {

    private final SeedRepository seedRepository;

    public SeedServiceImpl(SeedRepository seedRepository) {
        this.seedRepository = seedRepository;
    }

    @Override
    public Seed register(Seed seed) {

        Optional<Seed> existing = seedRepository.findByNormalizedSpeciesAndNormalizedCultivar(
                seed.getNormalizedSpecies(), seed.getNormalizedCultivar()
        );
        if (existing.isPresent()) {
            throw new BusinessException("Já existe uma semente com essa espécie e cultivar.");
        }

        return seedRepository.save(seed);
    }

    @Override
    public Optional<Seed> findById(Long id) {
        return seedRepository.findById(id);
    }
}
