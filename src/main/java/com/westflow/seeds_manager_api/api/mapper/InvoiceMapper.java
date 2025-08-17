package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.InvoiceResponse;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(source = "seedId", target = "seed.id")
    Invoice toDomain(InvoiceCreateRequest request);

    @Mapping(source = "seed.id", target = "seedId")
    InvoiceResponse toResponse(Invoice invoice);

}
