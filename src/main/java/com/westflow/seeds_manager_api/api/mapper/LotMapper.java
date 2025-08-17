package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.LotCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LotMapper {

    @Mapping(source = "seedId", target = "seed.id")
    @Mapping(source = "invoiceId", target = "invoice.id")
    Lot toDomain(LotCreateRequest request);

    @Mapping(source = "seed.id", target = "seedId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    LotResponse toResponse(Lot lot);
}
