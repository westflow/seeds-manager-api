package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.LotWithdrawal;

import java.util.Optional;

public interface LotWithdrawalRepository {
    LotWithdrawal save(LotWithdrawal lotWithdrawal);
    boolean existsByLotId(Long lotId);
    Optional<LotWithdrawal> findById(Long id);
}
