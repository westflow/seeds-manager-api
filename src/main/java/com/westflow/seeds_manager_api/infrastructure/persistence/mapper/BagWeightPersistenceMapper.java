package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.BagWeight;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagWeightEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BagWeightPersistenceMapper {

    BagWeightEntity toEntity(BagWeight domain);

    default BagWeight toDomain(BagWeightEntity entity) {
        if (entity == null) {
            return null;
        }

        return BagWeight.restore(
                entity.getId(),
                entity.getWeight(),
                entity.getActive()
        );
    }
}
