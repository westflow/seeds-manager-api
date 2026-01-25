package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.enums.LotStatus;
import com.westflow.seeds_manager_api.domain.model.LotReservation;
import com.westflow.seeds_manager_api.domain.repository.LotReservationRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotReservationEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.LotReservationPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaLotReservationRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class LotReservationRepositoryAdapter implements LotReservationRepository {
    private final JpaLotReservationRepository jpaRepository;
    private final LotReservationPersistenceMapper mapper;

    public LotReservationRepositoryAdapter (
        JpaLotReservationRepository jpaRepository,
        LotReservationPersistenceMapper mapper
    )
    {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public LotReservation save(LotReservation lotReservation) {
        LotReservationEntity entity = mapper.toEntity(lotReservation);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public boolean existsByLotId(Long lotId) {
        return jpaRepository.existsByLot_IdAndStatus(lotId, LotStatus.RESERVED);
    }

    @Override
    public Optional<LotReservation> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
}
