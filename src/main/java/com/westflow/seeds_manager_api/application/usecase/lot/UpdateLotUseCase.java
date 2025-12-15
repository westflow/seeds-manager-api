package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.api.dto.request.LotRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.api.mapper.LotMapper;
import com.westflow.seeds_manager_api.application.factory.LotFactory;
import com.westflow.seeds_manager_api.application.support.lot.LotContext;
import com.westflow.seeds_manager_api.application.support.lot.LotContextService;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import com.westflow.seeds_manager_api.domain.model.User;
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
public class UpdateLotUseCase {

    private final LotRepository lotRepository;
    private final LotInvoiceRepository lotInvoiceRepository;

    private final LotContextService lotContextService;
    private final LotDomainService lotDomainService;
    private final LotFactory lotFactory;
    private final LotMapper lotMapper;

    private final LotWithdrawalRepository lotWithdrawalRepository;
    private final LotReservationRepository lotReservationRepository;
    private final InvoiceAllocationService invoiceAllocationService;

    @Transactional
    public LotResponse execute(Long id, LotRequest request, User user) {

        Lot existingLot = lotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote", id));

        lotDomainService.validateUpdateAllowed(
                lotWithdrawalRepository.existsByLotId(id),
                lotReservationRepository.existsByLotId(id)
        );

        List<LotInvoice> currentAllocations =
                lotInvoiceRepository.findAllByLotId(id);

        invoiceAllocationService.restoreAllocations(currentAllocations);

        lotInvoiceRepository.deleteAllByLotId(id);

        LotContext ctx = lotContextService.load(request);

        Lot updatedLot = lotFactory.update(existingLot, request, ctx, user);

        lotDomainService.validateInvoices(updatedLot, ctx.invoices(), ctx.allocationMap());

        List<LotInvoice> newAllocations =
                lotDomainService.createAllocations(updatedLot, ctx.invoices(), ctx.allocationMap());

        invoiceAllocationService.applyAllocations(newAllocations);

        lotRepository.save(updatedLot);
        lotInvoiceRepository.saveAll(newAllocations);

        return lotMapper.toResponse(updatedLot, newAllocations);
    }
}
