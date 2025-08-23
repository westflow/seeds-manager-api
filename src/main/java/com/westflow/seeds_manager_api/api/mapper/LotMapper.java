package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.LotCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class LotMapper {

    @Mapping(source = "seed.id", target = "seedId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    public abstract LotResponse toResponse(Lot lot);

    public Lot toDomain(LotCreateRequest request, Seed seed, Invoice invoice, User user, String lotNumber) {
        return Lot.builder()
                .lotNumber(lotNumber)
                .lotType(request.getLotType())
                .seed(seed)
                .seedType(request.getSeedType())
                .category(request.getCategory())
                .bagWeight(request.getBagWeight())
                .balance(request.getBalance())
                .analysisBulletin(request.getAnalysisBulletin())
                .bulletinDate(request.getBulletinDate())
                .invoice(invoice)
                .bagType(request.getBagType())
                .validityDate(request.getValidityDate())
                .seedScore(request.getSeedScore())
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
