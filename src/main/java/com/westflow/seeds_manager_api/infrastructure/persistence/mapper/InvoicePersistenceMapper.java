package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {SeedPersistenceMapper.class})
public abstract class InvoicePersistenceMapper {

    protected SeedPersistenceMapper seedPersistenceMapper;

    @Autowired
    void setSeedPersistenceMapper(SeedPersistenceMapper mapper) {
        this.seedPersistenceMapper = mapper;
    }

    public abstract InvoiceEntity toEntity(Invoice domain);

    public Invoice toDomain(InvoiceEntity entity) {
        if (entity == null) {
            return null;
        }

        return Invoice.restore(
                entity.getId(),
                entity.getInvoiceNumber(),
                entity.getProducerName(),
                seedPersistenceMapper.toDomain(entity.getSeed()),
                entity.getTotalKg(),
                entity.getBalance(),
                entity.getOperationType(),
                entity.getAuthNumber(),
                entity.getCategory(),
                entity.getPurity(),
                entity.getHarvest(),
                entity.getProductionState(),
                entity.getPlantedArea(),
                entity.getApprovedArea(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getActive()
        );
    }
}
