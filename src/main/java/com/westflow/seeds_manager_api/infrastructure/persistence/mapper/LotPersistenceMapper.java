package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        SeedPersistenceMapper.class,
        InvoicePersistenceMapper.class
})
public interface LotPersistenceMapper {
    LotEntity toEntity(Lot domain);
    Lot toDomain(LotEntity entity);
}
