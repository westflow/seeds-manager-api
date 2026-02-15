package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.LotWithdrawal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LotWithdrawalRepository {
    LotWithdrawal save(LotWithdrawal lotWithdrawal);
    boolean existsByLotId(Long lotId);
    Optional<LotWithdrawal> findById(Long id);
    Page<LotWithdrawal> findByLotId(Long lotId, Pageable pageable);
}
