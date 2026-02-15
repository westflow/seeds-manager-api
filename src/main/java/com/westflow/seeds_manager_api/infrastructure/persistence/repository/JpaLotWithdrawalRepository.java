package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotWithdrawalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLotWithdrawalRepository extends JpaRepository<LotWithdrawalEntity,Long> {
    boolean existsByLot_IdAndActiveTrue(Long lotId);
    Page<LotWithdrawalEntity> findByLot_IdAndActiveTrue(Long lotId, Pageable pageable);
}
