package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.LotReservationRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.api.mapper.LotReservationMapper;
import com.westflow.seeds_manager_api.application.service.ClientService;
import com.westflow.seeds_manager_api.application.service.LotReservationService;
import com.westflow.seeds_manager_api.application.service.LotService;
import com.westflow.seeds_manager_api.domain.model.Client;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotReservation;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.LotReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LotReservationServiceImpl implements LotReservationService {

    private final LotReservationRepository lotReservationRepository;
    private final LotReservationMapper lotReservationMapper;
    private final ClientService clientService;
    private final LotService lotService;

    public LotReservationServiceImpl(LotReservationRepository lotReservationRepository,
                                     LotReservationMapper lotReservationMapper,
                                     ClientService clientService,
                                     LotService lotService) {
        this.lotReservationRepository = lotReservationRepository;
        this.lotReservationMapper = lotReservationMapper;
        this.clientService = clientService;
        this.lotService = lotService;
    }

    @Override
    @Transactional
    public LotReservationResponse reserve(LotReservationRequest request, User user) {

        Lot lot = lotService.findEntityById(request.getLotId())
                .orElseThrow(() -> new ResourceNotFoundException("Lote", request.getLotId()));

        Client client = clientService.findEntityById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", request.getClientId()));

        LotReservation reservation = lotReservationMapper.toDomain(request, user, lot, client);
        LotReservation saved = lotReservationRepository.save(reservation);
        lotService.updateBalance(lot, request.getQuantity());
        return lotReservationMapper.toResponse(saved);
    }
}
