package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.api.dto.request.LotReservationRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.api.mapper.LotReservationMapper;
import com.westflow.seeds_manager_api.application.usecase.client.FindClientByIdUseCase;
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
    private final FindClientByIdUseCase findClientByIdUseCase;

    @Transactional
    public LotReservationResponse execute(LotReservationRequest request, User user) {

        Lot lot = findLotByIdUseCase.execute(request.getLotId());

        Client client = findClientByIdUseCase.execute(request.getClientId());

        LotReservation reservation =
                mapper.toDomain(request, user, lot, client);

        LotReservation saved =
                lotReservationRepository.save(reservation);

        consumeLotBalanceUseCase.execute(lot, request.getQuantity());

        return mapper.toResponse(saved);
    }
}
