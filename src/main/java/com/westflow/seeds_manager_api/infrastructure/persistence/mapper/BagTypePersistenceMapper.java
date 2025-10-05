package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.entity.BagType;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BagTypePersistenceMapper {
    BagTypeEntity toEntity(BagType domain);
    BagType toDomain(BagTypeEntity entity);
}
