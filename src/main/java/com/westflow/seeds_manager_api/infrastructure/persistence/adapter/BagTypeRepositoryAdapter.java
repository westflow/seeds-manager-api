package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.entity.BagType;
import com.westflow.seeds_manager_api.domain.repository.BagTypeRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagTypeEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.BagTypePersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaBagTypeRepository;
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
}
