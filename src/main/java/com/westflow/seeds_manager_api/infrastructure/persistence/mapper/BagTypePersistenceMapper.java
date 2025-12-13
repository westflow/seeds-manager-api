package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.BagType;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BagTypePersistenceMapper {
    BagTypeEntity toEntity(BagType domain);
    BagType toDomain(BagTypeEntity entity);
}
