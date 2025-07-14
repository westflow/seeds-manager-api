package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.SeedEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeedPersistenceMapper {
    SeedEntity toEntity(Seed domain);
    Seed toDomain(SeedEntity entity);
}
