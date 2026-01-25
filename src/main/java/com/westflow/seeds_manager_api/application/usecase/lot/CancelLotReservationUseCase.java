package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotReservation;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import com.westflow.seeds_manager_api.domain.repository.LotReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelLotReservationUseCase {

    private final LotReservationRepository lotReservationRepository;
    private final LotRepository lotRepository;

    @Transactional
    public void execute(Long reservationId) {
        LotReservation reservation = lotReservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva de lote", reservationId));

        Lot lot = reservation.getLot();

        reservation.cancel();

        lot.increaseBalance(reservation.getQuantity());
        lotRepository.save(lot);

        lotReservationRepository.save(reservation);
    }
}
