package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.LotWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotWithdrawalRepository extends JpaRepository<LotWithdrawal, Long> {
}
