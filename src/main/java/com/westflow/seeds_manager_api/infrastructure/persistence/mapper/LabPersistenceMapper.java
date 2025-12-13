package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.Lab;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LabEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabPersistenceMapper {
    LabEntity toEntity(Lab domain);
    Lab toDomain(LabEntity entity);
}
