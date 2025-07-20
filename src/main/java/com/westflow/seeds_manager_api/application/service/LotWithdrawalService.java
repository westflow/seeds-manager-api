package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.LotWithdrawal;

public interface LotWithdrawalService {
    LotWithdrawal withdraw(LotWithdrawal withdrawal);
}
