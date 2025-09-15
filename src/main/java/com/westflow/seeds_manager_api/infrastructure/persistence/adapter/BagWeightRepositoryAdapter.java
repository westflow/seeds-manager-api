package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import com.westflow.seeds_manager_api.domain.repository.BagWeightRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagWeightEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.BagWeightPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaBagWeightRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.BagWeightSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    @Override
    public Optional<BagWeight> findByWeight(BigDecimal weight) {
        return jpaRepository.findByWeight(weight)
                .map(mapper::toDomain);
    }

    @Override
    public Page<BagWeight> findAll(Pageable pageable) {
        Specification<BagWeightEntity> spec = BagWeightSpecifications.isActive();
        return jpaRepository.findAll(spec, pageable)
                .map(mapper::toDomain);
    }
}
