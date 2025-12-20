package com.westflow.seeds_manager_api.application.support.lot;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceAllocationRequest;
import com.westflow.seeds_manager_api.api.dto.request.LotRequest;
import com.westflow.seeds_manager_api.application.service.InvoiceService;
import com.westflow.seeds_manager_api.application.usecase.lab.FindLabByIdUseCase;
import com.westflow.seeds_manager_api.application.usecase.bagtype.FindBagTypeByIdUseCase;
import com.westflow.seeds_manager_api.application.usecase.bagweight.FindBagWeightByIdUseCase;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.BagType;
import com.westflow.seeds_manager_api.domain.model.BagWeight;
import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.model.Lab;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotContextService {

    private final FindBagWeightByIdUseCase findBagWeightByIdUseCase;
    private final FindBagTypeByIdUseCase findBagTypeByIdUseCase;
    private final FindLabByIdUseCase findLabByIdUseCase;
    private final InvoiceService invoiceService;

    public LotContext load(LotRequest request) {

        Map<Long, BigDecimal> allocationMap =
                request.getInvoiceAllocations() == null
                        ? Map.of()
                        : request.getInvoiceAllocations().stream()
                        .filter(a -> a.getInvoiceId() != null && a.getQuantity() != null)
                        .collect(Collectors.toMap(
                                InvoiceAllocationRequest::getInvoiceId,
                                InvoiceAllocationRequest::getQuantity,
                                BigDecimal::add
                        ));

        List<Invoice> invoices = allocationMap.keySet().stream()
                .map(id -> invoiceService.findEntityById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Nota fiscal", id)))
                .toList();

        BagWeight bagWeight = findBagWeightByIdUseCase.execute(request.getBagWeightId());

        BagType bagType = findBagTypeByIdUseCase.execute(request.getBagTypeId());

        Lab lab = request.getLabId() == null || request.getLabId() <= 0
                ? null
                : findLabByIdUseCase.execute(request.getLabId());

        return new LotContext(bagWeight, bagType, lab, invoices, allocationMap);
    }
}
