package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.model.BagType;
import com.westflow.seeds_manager_api.domain.repository.BagTypeRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagTypeEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.BagTypePersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaBagTypeRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.BagTypeSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BagTypeRepositoryAdapter implements BagTypeRepository {

    private final JpaBagTypeRepository jpaRepository;
    private final BagTypePersistenceMapper mapper;

    public BagTypeRepositoryAdapter(JpaBagTypeRepository jpaRepository, BagTypePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public BagType save(BagType bagType) {
        BagTypeEntity entity = mapper.toEntity(bagType);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<BagType> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Page<BagType> findAll(Pageable pageable) {
        Specification<BagTypeEntity> spec = BagTypeSpecifications.isActive();
        return jpaRepository.findAll(spec, pageable)
                .map(mapper::toDomain);
    }
}
