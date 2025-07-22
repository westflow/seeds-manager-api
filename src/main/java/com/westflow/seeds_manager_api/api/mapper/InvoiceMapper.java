package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.InvoiceResponse;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    Invoice toDomain(InvoiceCreateRequest request);

    InvoiceResponse toResponse(Invoice invoice);

}
