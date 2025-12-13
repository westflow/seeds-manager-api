package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.LotSequence;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotSequenceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LotSequencePersistenceMapper {
    LotSequenceEntity toEntity(LotSequence domain);
    LotSequence toDomain(LotSequenceEntity entity);
}
