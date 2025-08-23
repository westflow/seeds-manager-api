package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagWeightEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BagWeightPersistenceMapper {
    BagWeightEntity toEntity(BagWeight domain);
    BagWeight toDomain(BagWeightEntity entity);
}
