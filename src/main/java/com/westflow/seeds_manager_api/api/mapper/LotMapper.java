package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.response.InvoiceAllocationResponse;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LotMapper {

    @Mapping(source = "bagWeight.id", target = "bagWeightId")
    @Mapping(source = "bagType.id", target = "bagTypeId")
    @Mapping(source = "lab.id", target = "labId")
    public abstract LotResponse mapLot(Lot lot);

    public LotResponse toResponse(Lot lot, List<LotInvoice> allocations) {
        LotResponse response = mapLot(lot);
        response.setInvoiceAllocations(toInvoiceAllocations(allocations));
        return response;
    }

    protected InvoiceAllocationResponse map(LotInvoice lotInvoice) {
        if (lotInvoice == null) {
            return null;
        }
        return InvoiceAllocationResponse.builder()
                .invoiceId(lotInvoice.getInvoice().getId())
                .quantity(lotInvoice.getAllocatedQuantityLot())
                .build();
    }

    public List<InvoiceAllocationResponse> toInvoiceAllocations(List<LotInvoice> lotInvoices) {
        if (lotInvoices == null) return List.of();
        return lotInvoices.stream()
                .map(this::map)
                .toList();
    }
}
