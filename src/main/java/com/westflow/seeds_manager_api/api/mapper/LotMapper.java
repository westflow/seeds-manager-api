package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.LotRequest;
import com.westflow.seeds_manager_api.api.dto.response.InvoiceAllocationResponse;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.domain.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LotMapper {

    @Mapping(source = "bagWeight.id", target = "bagWeightId")
    @Mapping(source = "bagType.id", target = "bagTypeId")
    @Mapping(source = "lab.id", target = "labId")
    public abstract LotResponse toResponse(Lot lot);

    public Lot toDomain(LotRequest request, BagWeight bagWeight, BagType bagType, Lab lab, User user, String lotNumber) {
        return Lot.builder()
                .lotNumber(lotNumber)
                .lotType(request.getLotType())
                .seedType(request.getSeedType())
                .category(request.getCategory())
                .bagWeight(bagWeight)
                .bagType(bagType)
                .quantityTotal(request.getQuantityTotal())
                .balance(request.getQuantityTotal())
                .productionOrder(request.getProductionOrder())
                .analysisBulletin(request.getAnalysisBulletin())
                .bulletinDate(request.getBulletinDate())
                .hardSeeds(request.getHardSeeds())
                .wildSeeds(request.getWildSeeds())
                .otherCultivatedSpecies(request.getOtherCultivatedSpecies())
                .tolerated(request.getTolerated())
                .prohibited(request.getProhibited())
                .validityDate(request.getValidityDate())
                .seedScore(request.getSeedScore())
                .purity(request.getPurity())
                .lab(lab)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Lot toUpdatedDomain(Lot existingLot, LotRequest request,
                               BagWeight bagWeight, BagType bagType, Lab lab) {
        return Lot.builder()
                .id(existingLot.getId())
                .lotNumber(existingLot.getLotNumber())
                .lotType(request.getLotType())
                .seedType(request.getSeedType())
                .category(request.getCategory())
                .bagWeight(bagWeight)
                .bagType(bagType)
                .quantityTotal(request.getQuantityTotal())
                .balance(request.getQuantityTotal())
                .productionOrder(request.getProductionOrder())
                .analysisBulletin(request.getAnalysisBulletin())
                .bulletinDate(request.getBulletinDate())
                .hardSeeds(request.getHardSeeds())
                .wildSeeds(request.getWildSeeds())
                .otherCultivatedSpecies(request.getOtherCultivatedSpecies())
                .tolerated(request.getTolerated())
                .prohibited(request.getProhibited())
                .validityDate(request.getValidityDate())
                .seedScore(request.getSeedScore())
                .purity(request.getPurity())
                .lab(lab)
                .user(existingLot.getUser())
                .createdAt(existingLot.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .active(existingLot.getActive())
                .build();
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
