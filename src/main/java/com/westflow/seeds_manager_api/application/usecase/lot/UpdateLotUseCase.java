package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.api.dto.request.LotRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.api.mapper.LotMapper;
import com.westflow.seeds_manager_api.application.factory.LotFactory;
import com.westflow.seeds_manager_api.application.support.lot.LotContext;
import com.westflow.seeds_manager_api.application.support.lot.LotContextService;
import com.westflow.seeds_manager_api.application.support.lot.LotModificationPreparationService;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.LotInvoiceRepository;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import com.westflow.seeds_manager_api.domain.service.InvoiceAllocationService;
import com.westflow.seeds_manager_api.domain.service.LotDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateLotUseCase {

    private final LotRepository lotRepository;
    private final LotInvoiceRepository lotInvoiceRepository;

    private final LotContextService lotContextService;
    private final LotDomainService lotDomainService;
    private final LotFactory lotFactory;
    private final LotMapper lotMapper;

    private final InvoiceAllocationService invoiceAllocationService;
    private final LotModificationPreparationService lotModificationPreparationService;
    private final FindLotByIdUseCase findLotByIdUseCase;

    @Transactional
    public LotResponse execute(Long id, LotRequest request, User user) {

        Lot existingLot = findLotByIdUseCase.execute(id);

        lotModificationPreparationService.prepare(id);

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
