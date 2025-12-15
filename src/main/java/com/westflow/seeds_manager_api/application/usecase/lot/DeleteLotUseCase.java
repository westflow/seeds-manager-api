package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.application.support.lot.LotModificationPreparationService;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import com.westflow.seeds_manager_api.domain.repository.LotInvoiceRepository;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import com.westflow.seeds_manager_api.domain.repository.LotReservationRepository;
import com.westflow.seeds_manager_api.domain.repository.LotWithdrawalRepository;
import com.westflow.seeds_manager_api.domain.service.InvoiceAllocationService;
import com.westflow.seeds_manager_api.domain.service.LotDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteLotUseCase {

    private final LotRepository lotRepository;
    private final LotModificationPreparationService lotModificationPreparationService;

    @Transactional
    public void execute(Long id) {
        Lot existingLot = lotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote", id));

        lotModificationPreparationService.prepare(id);

        existingLot.deactivate();
        lotRepository.save(existingLot);
    }
}
