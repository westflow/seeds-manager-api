package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.InvoiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SeedPersistenceMapper.class})
public interface InvoicePersistenceMapper {
    InvoiceEntity toEntity(Invoice domain);
    Invoice toDomain(InvoiceEntity entity);
}
