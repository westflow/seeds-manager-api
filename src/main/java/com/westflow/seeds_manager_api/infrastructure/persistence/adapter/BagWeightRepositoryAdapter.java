package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import com.westflow.seeds_manager_api.domain.repository.BagWeightRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagWeightEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.BagWeightPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaBagWeightRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BagWeightRepositoryAdapter implements BagWeightRepository {

    private final JpaBagWeightRepository jpaRepository;
    private final BagWeightPersistenceMapper mapper;

    public BagWeightRepositoryAdapter(JpaBagWeightRepository jpaRepository, BagWeightPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public BagWeight save(BagWeight bagWeight) {
        BagWeightEntity entity = mapper.toEntity(bagWeight);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<BagWeight> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
}
