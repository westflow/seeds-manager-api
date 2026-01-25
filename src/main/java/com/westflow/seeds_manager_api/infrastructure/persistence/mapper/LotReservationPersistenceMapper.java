package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.LotReservation;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {
        LotPersistenceMapper.class,
        ClientPersistenceMapper.class,
        UserPersistenceMapper.class
})
public abstract class LotReservationPersistenceMapper {

    protected LotPersistenceMapper lotPersistenceMapper;
    protected ClientPersistenceMapper clientPersistenceMapper;
    protected UserPersistenceMapper userPersistenceMapper;

    @Autowired
    void setLotPersistenceMapper(LotPersistenceMapper mapper) { this.lotPersistenceMapper = mapper; }

    @Autowired
    void setClientPersistenceMapper(ClientPersistenceMapper mapper) { this.clientPersistenceMapper = mapper; }

    @Autowired
    void setUserPersistenceMapper(UserPersistenceMapper mapper) { this.userPersistenceMapper = mapper; }

    public abstract LotReservationEntity toEntity(LotReservation domain);

    public abstract LotReservation toDomain(LotReservationEntity entity);

    @ObjectFactory
    protected LotReservation createLotReservation(LotReservationEntity entity) {
        if (entity == null) return null;

        return LotReservation.restore(
                entity.getId(),
                lotPersistenceMapper.toDomain(entity.getLot()),
                entity.getIdentification(),
                entity.getQuantity(),
                entity.getReservationDate(),
                entity.getStatus(),
                clientPersistenceMapper.toDomain(entity.getClient()),
                userPersistenceMapper.toDomain(entity.getUser()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
