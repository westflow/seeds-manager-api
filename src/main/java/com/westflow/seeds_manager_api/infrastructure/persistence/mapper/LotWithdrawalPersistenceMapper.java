package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.LotWithdrawal;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotWithdrawalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {
        LotPersistenceMapper.class,
        ClientPersistenceMapper.class,
        UserPersistenceMapper.class
})
public abstract class LotWithdrawalPersistenceMapper {

    protected UserPersistenceMapper userPersistenceMapper;
    protected ClientPersistenceMapper clientPersistenceMapper;
    protected LotPersistenceMapper lotPersistenceMapper;

    @Autowired
    void setUserPersistenceMapper(UserPersistenceMapper mapper) { this.userPersistenceMapper = mapper; }

    @Autowired
    void setClientPersistenceMapper(ClientPersistenceMapper mapper) { this.clientPersistenceMapper = mapper; }

    @Autowired
    void setLotPersistenceMapper(LotPersistenceMapper mapper) { this.lotPersistenceMapper = mapper; }

    public abstract LotWithdrawalEntity toEntity(LotWithdrawal domain);

    public abstract LotWithdrawal toDomain(LotWithdrawalEntity entity);

    @ObjectFactory
    protected LotWithdrawal createLotWithdrawal(LotWithdrawalEntity entity) {
        return LotWithdrawal.restore(
                entity.getId(),
                lotPersistenceMapper.toDomain(entity.getLot()),
                entity.getInvoiceNumber(),
                entity.getQuantity(),
                entity.getWithdrawalDate(),
                entity.getState(),
                userPersistenceMapper.toDomain(entity.getUser()),
                clientPersistenceMapper.toDomain(entity.getClient()),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.isActive()
        );
    }
}
