package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.Seed;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.SeedEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeedPersistenceMapper {

    SeedEntity toEntity(Seed domain);

    default Seed toDomain(SeedEntity entity) {
        if (entity == null) {
            return null;
        }

        return Seed.restore(
                entity.getId(),
                entity.getSpecies(),
                entity.getCultivar(),
                entity.isProtected(),
                entity.getNormalizedSpecies(),
                entity.getNormalizedCultivar(),
                entity.getActive()
        );
    }
}
