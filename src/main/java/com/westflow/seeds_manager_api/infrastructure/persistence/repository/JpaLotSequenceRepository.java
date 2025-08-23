package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotSequenceEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaLotSequenceRepository extends JpaRepository<LotSequenceEntity,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM LotSequenceEntity l WHERE l.resetDone = false ORDER BY l.year DESC")
    Optional<LotSequenceEntity> findTopByResetDoneFalseOrderByYearDesc();

    boolean existsByResetDoneFalse();
}
