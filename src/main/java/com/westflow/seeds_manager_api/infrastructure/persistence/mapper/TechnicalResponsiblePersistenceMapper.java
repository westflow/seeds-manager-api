package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.TechnicalResponsible;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.TechnicalResponsibleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnicalResponsiblePersistenceMapper {

    TechnicalResponsibleEntity toEntity(TechnicalResponsible domain);

    default TechnicalResponsible toDomain(TechnicalResponsibleEntity entity) {
        if (entity == null) return null;

        return TechnicalResponsible.restore(
                entity.getId(),
                entity.getCompanyId(),
                entity.getName(),
                entity.getCpf(),
                entity.getRenasemNumber(),
                entity.getCreaNumber(),
                entity.getAddress(),
                entity.getCity(),
                entity.getState(),
                entity.getZipCode(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getIsPrimary(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getActive()
        );
    }
}

