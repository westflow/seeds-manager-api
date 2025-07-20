package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.LotWithdrawalService;
import com.westflow.seeds_manager_api.domain.entity.LotWithdrawal;
import com.westflow.seeds_manager_api.domain.repository.LotWithdrawalRepository;
import org.springframework.stereotype.Service;

@Service
public class LotWithdrawalServiceImpl implements LotWithdrawalService {

    private final LotWithdrawalRepository lotWithdrawalRepository;

    public LotWithdrawalServiceImpl(LotWithdrawalRepository lotWithdrawalRepository) {
        this.lotWithdrawalRepository = lotWithdrawalRepository;
    }

    @Override
    public LotWithdrawal withdraw(LotWithdrawal withdrawal) {
        return lotWithdrawalRepository.save(withdrawal);
    }
}
