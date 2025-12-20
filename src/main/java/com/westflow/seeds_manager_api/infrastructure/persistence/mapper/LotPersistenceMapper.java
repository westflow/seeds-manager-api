package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.*;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        uses = {
                SeedPersistenceMapper.class,
                InvoicePersistenceMapper.class,
                UserPersistenceMapper.class,
                LabPersistenceMapper.class
        }
)
public abstract class LotPersistenceMapper {

    protected UserPersistenceMapper userPersistenceMapper;

    @Autowired
    void setUserPersistenceMapper(UserPersistenceMapper mapper) {
        this.userPersistenceMapper = mapper;
    }

    public abstract LotEntity toEntity(Lot domain);

    public abstract Lot toDomain(LotEntity entity);

    @ObjectFactory
    protected Lot createLot(LotEntity entity) {
        return Lot.restore(
                entity.getId(),
                entity.getLotNumber(),
                entity.getLotType(),
                entity.getSeedType(),
                entity.getCategory(),
                map(entity.getBagWeight()),
                map(entity.getBagType()),
                entity.getQuantityTotal(),
                entity.getBalance(),
                entity.getProductionOrder(),
                entity.getAnalysisBulletin(),
                entity.getBulletinDate(),
                entity.getHardSeeds(),
                entity.getWildSeeds(),
                entity.getOtherCultivatedSpecies(),
                entity.getTolerated(),
                entity.getProhibited(),
                entity.getValidityDate(),
                entity.getSeedScore(),
                entity.getPurity(),
                userPersistenceMapper.toDomain(entity.getUser()),
                map(entity.getLab()),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.isActive()
        );
    }

    protected BagType map(BagTypeEntity entity) {
        if (entity == null) return null;

        return BagType.restore(
                entity.getId(),
                entity.getName(),
                entity.getActive()
        );
    }

    protected abstract BagWeight map(BagWeightEntity entity);
    protected abstract Lab map(LabEntity entity);
}
