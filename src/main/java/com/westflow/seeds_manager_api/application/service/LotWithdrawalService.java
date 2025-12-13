package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.LotWithdrawalRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
import com.westflow.seeds_manager_api.domain.model.User;

public interface LotWithdrawalService {
    LotWithdrawalResponse withdraw(LotWithdrawalRequest request, User user);
}
