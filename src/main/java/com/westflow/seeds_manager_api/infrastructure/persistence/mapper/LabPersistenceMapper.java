package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.Lab;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LabEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabPersistenceMapper {

    LabEntity toEntity(Lab domain);

    default Lab toDomain(LabEntity entity) {
        if (entity == null) {
            return null;
        }

        return Lab.restore(
                entity.getId(),
                entity.getName(),
                entity.getState(),
                entity.getRenasemCode(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getActive()
        );
    }
}
