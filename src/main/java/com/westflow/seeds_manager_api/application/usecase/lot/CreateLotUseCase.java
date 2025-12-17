package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.api.dto.request.LotRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.api.mapper.LotMapper;
import com.westflow.seeds_manager_api.application.factory.LotFactory;
import com.westflow.seeds_manager_api.application.support.lot.LotContext;
import com.westflow.seeds_manager_api.application.support.lot.LotContextService;
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
public class CreateLotUseCase {

    private final LotRepository lotRepository;
    private final LotContextService lotContextService;
    private final LotDomainService lotDomainService;
    private final LotFactory lotFactory;
    private final LotInvoiceRepository lotInvoiceRepository;
    private final LotMapper lotMapper;
    private final InvoiceAllocationService invoiceAllocationService;

    @Transactional
    public LotResponse execute(LotRequest request, User user) {

        LotContext ctx = lotContextService.load(request);

        Lot lot = lotFactory.create(request, ctx, user);

        lotDomainService.validateInvoices(lot, ctx.invoices(), ctx.allocationMap());

        Lot savedLot = lotRepository.save(lot);

        List<LotInvoice> allocations =
                lotDomainService.createAllocations(savedLot, ctx.invoices(), ctx.allocationMap());

        invoiceAllocationService.applyAllocations(allocations);

        lotInvoiceRepository.saveAll(allocations);

        return lotMapper.toResponse(savedLot, allocations);
    }
}
