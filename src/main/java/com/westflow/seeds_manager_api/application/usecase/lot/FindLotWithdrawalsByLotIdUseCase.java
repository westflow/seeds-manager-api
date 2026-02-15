package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
import com.westflow.seeds_manager_api.api.mapper.LotWithdrawalMapper;
import com.westflow.seeds_manager_api.domain.model.LotWithdrawal;
import com.westflow.seeds_manager_api.domain.repository.LotWithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindLotWithdrawalsByLotIdUseCase {

    private final LotWithdrawalRepository lotWithdrawalRepository;
    private final LotWithdrawalMapper mapper;

    public Page<LotWithdrawalResponse> execute(Long lotId, Pageable pageable) {
        Page<LotWithdrawal> page = lotWithdrawalRepository.findByLotId(lotId, pageable);
        return page.map(mapper::toResponse);
    }
}
