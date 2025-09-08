package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.LotCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.entity.User;

import java.math.BigDecimal;
import java.util.Optional;

public interface LotService {
    LotResponse register(LotCreateRequest request, User user);
    Optional<Lot> findById(Long id);
    void updateBalance(Lot lot, BigDecimal allocated);
}
