package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.api.mapper.LotReservationMapper;
import com.westflow.seeds_manager_api.domain.model.LotReservation;
import com.westflow.seeds_manager_api.domain.repository.LotReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindLotReservationsByLotIdUseCase {

    private final LotReservationRepository lotReservationRepository;
    private final LotReservationMapper mapper;

    public Page<LotReservationResponse> execute(Long lotId, Pageable pageable) {
        Page<LotReservation> page = lotReservationRepository.findByLotId(lotId, pageable);
        return page.map(mapper::toResponse);
    }
}
