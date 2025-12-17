package com.westflow.seeds_manager_api.application.support.lot;

import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import com.westflow.seeds_manager_api.domain.repository.LotInvoiceRepository;
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
public class LotModificationPreparationService {

    private final LotDomainService lotDomainService;
    private final LotInvoiceRepository lotInvoiceRepository;
    private final InvoiceAllocationService invoiceAllocationService;
    private final LotWithdrawalRepository lotWithdrawalRepository;
    private final LotReservationRepository lotReservationRepository;

    @Transactional
    public void prepare(Long lotId) {

        lotDomainService.validateUpdateAllowed(
                lotWithdrawalRepository.existsByLotId(lotId),
                lotReservationRepository.existsByLotId(lotId)
        );

        List<LotInvoice> allocations =
                lotInvoiceRepository.findAllByLotId(lotId);

        invoiceAllocationService.restoreAllocations(allocations);

        lotInvoiceRepository.deleteAllByLotId(lotId);
    }
}
