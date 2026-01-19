package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.LotSequence;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotSequenceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LotSequencePersistenceMapper {

    LotSequenceEntity toEntity(LotSequence domain);

    default LotSequence toDomain(LotSequenceEntity entity) {
        if (entity == null) {
            return null;
        }

        return LotSequence.restore(
                entity.getId(),
                entity.getYear(),
                entity.getLastNumber(),
                Boolean.TRUE.equals(entity.getResetDone()),
                entity.getResetDate(),
                entity.getCreatedAt()
        );
    }
}
