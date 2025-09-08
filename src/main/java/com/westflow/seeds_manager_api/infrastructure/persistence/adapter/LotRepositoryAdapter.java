package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.LotPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaLotRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LotRepositoryAdapter implements LotRepository {
    private final JpaLotRepository jpaRepository;
    private final LotPersistenceMapper mapper;

    public LotRepositoryAdapter(JpaLotRepository jpaRepository, LotPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Lot save(Lot lot) {
        LotEntity entity = mapper.toEntity(lot);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Lot> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
