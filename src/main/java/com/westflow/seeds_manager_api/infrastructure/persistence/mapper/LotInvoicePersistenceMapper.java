package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotInvoiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        LotPersistenceMapper.class,
        InvoicePersistenceMapper.class
})
public interface LotInvoicePersistenceMapper {

    LotInvoiceEntity toEntity(LotInvoice domain);
    LotInvoice toDomain(LotInvoiceEntity entity);
}
