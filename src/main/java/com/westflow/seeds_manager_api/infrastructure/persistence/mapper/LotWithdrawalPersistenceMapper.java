package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.entity.LotWithdrawal;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotWithdrawalEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        LotPersistenceMapper.class,
        ClientPersistenceMapper.class
})
public interface LotWithdrawalPersistenceMapper {
    LotWithdrawalEntity toEntity(LotWithdrawal domain);
    LotWithdrawal toDomain(LotWithdrawalEntity entity);
}
