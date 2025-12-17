package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.api.dto.request.LotReservationRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.api.mapper.LotReservationMapper;
import com.westflow.seeds_manager_api.application.service.ClientService;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Client;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotReservation;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.LotReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReserveLotUseCase {

    private final LotReservationRepository lotReservationRepository;
    private final LotReservationMapper mapper;
    private final FindLotByIdUseCase findLotByIdUseCase;
    private final ConsumeLotBalanceUseCase consumeLotBalanceUseCase;
    private final ClientService clientService;

    @Transactional
    public LotReservationResponse execute(LotReservationRequest request, User user) {

        Lot lot = findLotByIdUseCase.execute(request.getLotId());

        Client client = clientService.findEntityById(request.getClientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente", request.getClientId())
                );

        LotReservation reservation =
                mapper.toDomain(request, user, lot, client);

        LotReservation saved =
                lotReservationRepository.save(reservation);

        consumeLotBalanceUseCase.execute(lot, request.getQuantity());

        return mapper.toResponse(saved);
    }
}
