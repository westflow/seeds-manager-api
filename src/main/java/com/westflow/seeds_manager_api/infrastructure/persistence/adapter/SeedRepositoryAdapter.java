package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.SeedEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.SeedPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaSeedRepository;
import org.springframework.stereotype.Component;

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

}
