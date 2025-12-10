package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.LotRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

public interface LotService {

    LotResponse register(LotRequest request, User user);

    LotResponse findById(Long id);

    Optional<Lot> findEntityById(Long id);

    Page<LotResponse> findAll(Pageable pageable);

    void delete(Long id);

    void updateBalance(Lot lot, BigDecimal allocated);
}
