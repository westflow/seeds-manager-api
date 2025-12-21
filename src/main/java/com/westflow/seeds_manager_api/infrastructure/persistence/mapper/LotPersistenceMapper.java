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

    protected BagWeight map(BagWeightEntity entity) {
        if (entity == null) return null;

        return BagWeight.restore(
                entity.getId(),
                entity.getWeight(),
                entity.getActive()
        );
    }

    protected Lab map(LabEntity entity) {
        if (entity == null) return null;

        return Lab.restore(
                entity.getId(),
                entity.getName(),
                entity.getState(),
                entity.getRenasemCode(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getActive()
        );
    }

    protected Seed map(SeedEntity entity) {
        if (entity == null) return null;

        return Seed.restore(
                entity.getId(),
                entity.getSpecies(),
                entity.getCultivar(),
                entity.isProtected(),
                entity.getNormalizedSpecies(),
                entity.getNormalizedCultivar(),
                entity.getActive()
        );
    }
}
