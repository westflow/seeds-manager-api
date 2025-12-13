package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.Seed;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.SeedEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeedPersistenceMapper {
    SeedEntity toEntity(Seed domain);

    @Mapping(source = "protected", target = "isProtected")
    Seed toDomain(SeedEntity entity);
}
