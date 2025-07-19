package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.LotWithdrawal;

public interface LotWithdrawalRepository {
    LotWithdrawal save(LotWithdrawal lotWithdrawal);
}
