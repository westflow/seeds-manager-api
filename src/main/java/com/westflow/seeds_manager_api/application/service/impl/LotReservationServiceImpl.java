package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.LotReservationRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.api.mapper.LotReservationMapper;
import com.westflow.seeds_manager_api.application.service.ClientService;
import com.westflow.seeds_manager_api.application.service.LotReservationService;
import com.westflow.seeds_manager_api.application.usecase.lot.ConsumeLotBalanceUseCase;
import com.westflow.seeds_manager_api.application.usecase.lot.FindLotByIdUseCase;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Client;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotReservation;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.LotReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LotReservationServiceImpl implements LotReservationService {

    private final LotReservationRepository lotReservationRepository;
    private final LotReservationMapper lotReservationMapper;
    private final ClientService clientService;
    private final FindLotByIdUseCase findLotByIdUseCase;
    private final ConsumeLotBalanceUseCase consumeLotBalanceUseCase;

    @Override
    @Transactional
    public LotReservationResponse reserve(LotReservationRequest request, User user) {

        Lot lot = findLotByIdUseCase.execute(request.getLotId());

        Client client = clientService.findEntityById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", request.getClientId()));

        LotReservation reservation = lotReservationMapper.toDomain(request, user, lot, client);
        LotReservation saved = lotReservationRepository.save(reservation);
        consumeLotBalanceUseCase.execute(lot, request.getQuantity());
        return lotReservationMapper.toResponse(saved);
    }
}
