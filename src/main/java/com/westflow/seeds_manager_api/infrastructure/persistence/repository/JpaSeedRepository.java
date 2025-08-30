package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.SeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSeedRepository extends JpaRepository<SeedEntity, Long> {
    Optional<SeedEntity> findByNormalizedSpeciesAndNormalizedCultivar(String normalizedSpecies, String normalizedCultivar);
}
