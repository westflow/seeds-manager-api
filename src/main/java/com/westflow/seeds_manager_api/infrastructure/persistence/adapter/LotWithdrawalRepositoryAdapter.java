package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.model.LotWithdrawal;
import com.westflow.seeds_manager_api.domain.repository.LotWithdrawalRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotWithdrawalEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.LotWithdrawalPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaLotWithdrawalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LotWithdrawalRepositoryAdapter implements LotWithdrawalRepository {
    private final JpaLotWithdrawalRepository jpaRepository;
    private final LotWithdrawalPersistenceMapper mapper;

    public LotWithdrawalRepositoryAdapter (
            JpaLotWithdrawalRepository jpaRepository,
            LotWithdrawalPersistenceMapper mapper
    )
    {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public LotWithdrawal save(LotWithdrawal lotWithdrawal) {
        LotWithdrawalEntity entity = mapper.toEntity(lotWithdrawal);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public boolean existsByLotId(Long lotId) {
        return jpaRepository.existsByLot_IdAndActiveTrue(lotId);
    }

    @Override
    public Optional<LotWithdrawal> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<LotWithdrawal> findByLotId(Long lotId, Pageable pageable) {
        Page<LotWithdrawalEntity> page = jpaRepository.findByLot_IdAndActiveTrue(lotId, pageable);
        return page.map(mapper::toDomain);
    }
}
