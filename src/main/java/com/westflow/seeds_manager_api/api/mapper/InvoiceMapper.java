package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.InvoiceResponse;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class InvoiceMapper {

    @Mapping(source = "seed.id", target = "seedId")
    public abstract InvoiceResponse toResponse(Invoice invoice);

    public Invoice toDomain(InvoiceCreateRequest request, Seed seed) {
        return Invoice.builder()
                .invoiceNumber(request.getInvoiceNumber())
                .producerName(request.getProducerName())
                .seed(seed)
                .totalKg(request.getTotalKg())
                .operationType(request.getOperationType())
                .authNumber(request.getAuthNumber())
                .category(request.getCategory())
                .purity(request.getPurity())
                .harvest(request.getHarvest())
                .productionState(request.getProductionState())
                .plantedArea(request.getPlantedArea())
                .approvedArea(request.getApprovedArea())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
