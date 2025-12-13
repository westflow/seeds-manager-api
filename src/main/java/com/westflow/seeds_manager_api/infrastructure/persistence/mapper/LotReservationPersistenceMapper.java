package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.LotReservation;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotReservationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        LotPersistenceMapper.class,
        ClientPersistenceMapper.class,
        UserPersistenceMapper.class
})
public interface LotReservationPersistenceMapper {
    LotReservationEntity toEntity(LotReservation domain);
    LotReservation toDomain(LotReservationEntity entity);
}
