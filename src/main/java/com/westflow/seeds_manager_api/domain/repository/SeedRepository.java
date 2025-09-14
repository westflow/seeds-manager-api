package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.Seed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.SeedEntity;

import java.util.Optional;

public interface SeedRepository {
    Seed save(Seed seed);
    Optional<Seed> findById(Long id);
    Optional<Seed> findByNormalizedSpeciesAndNormalizedCultivar(String normalizedSpecies, String normalizedCultivar);
    Page<Seed> findAll(Specification<SeedEntity> spec, Pageable pageable);
    void deleteById(Long id);
}
