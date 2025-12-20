package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.response.InvoiceResponse;
import com.westflow.seeds_manager_api.domain.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class InvoiceMapper {

    @Mapping(source = "seed.id", target = "seedId")
    public abstract InvoiceResponse toResponse(Invoice invoice);
}
