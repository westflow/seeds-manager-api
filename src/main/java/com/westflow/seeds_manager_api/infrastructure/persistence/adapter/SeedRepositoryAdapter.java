package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.SeedEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.SeedPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaSeedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeedRepositoryAdapter implements SeedRepository {
    private final JpaSeedRepository jpaRepository;
    private final SeedPersistenceMapper mapper;

    public SeedRepositoryAdapter(JpaSeedRepository jpaRepository, SeedPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Seed save(Seed seed) {
        SeedEntity entity = mapper.toEntity(seed);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Seed> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Seed> findByNormalizedSpeciesAndNormalizedCultivar(String normalizedSpecies, String normalizedCultivar) {
        return jpaRepository.findByNormalizedSpeciesAndNormalizedCultivar(normalizedSpecies, normalizedCultivar)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Page<Seed> findAll(Specification<SeedEntity> spec, Pageable pageable) {
        return jpaRepository.findAll(spec, pageable).map(mapper::toDomain);
    }

}
