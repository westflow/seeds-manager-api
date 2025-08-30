package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.LotCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.domain.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LotMapper {

    @Mapping(source = "invoices", target = "invoiceIds")
    @Mapping(source = "bagWeight.id", target = "bagWeightId")
    @Mapping(source = "bagType.id", target = "bagTypeId")
    @Mapping(source = "lab.id", target = "labId")
    public abstract LotResponse toResponse(Lot lot);

    public Lot toDomain(LotCreateRequest request, List<Invoice> invoices, BagWeight bagWeight, BagType bagType, Lab lab, User user, String lotNumber) {
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
                .invoices(invoices)
                .validityDate(request.getValidityDate())
                .seedScore(request.getSeedScore())
                .purity(request.getPurity())
                .lab(lab)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    protected List<Long> mapInvoiceToIds(List<Invoice> invoices) {
        return invoices.stream()
                .map(Invoice::getId)
                .toList();
    }
}
