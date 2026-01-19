package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotInvoiceEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {
        LotPersistenceMapper.class,
        InvoicePersistenceMapper.class
})
public abstract class LotInvoicePersistenceMapper {

    protected LotPersistenceMapper lotPersistenceMapper;
    protected InvoicePersistenceMapper invoicePersistenceMapper;

    @Autowired
    void setLotPersistenceMapper(LotPersistenceMapper mapper) {
        this.lotPersistenceMapper = mapper;
    }

    @Autowired
    void setInvoicePersistenceMapper(InvoicePersistenceMapper mapper) {
        this.invoicePersistenceMapper = mapper;
    }

    public abstract LotInvoiceEntity toEntity(LotInvoice domain);

    public LotInvoice toDomain(LotInvoiceEntity entity) {
        if (entity == null) {
            return null;
        }

        return LotInvoice.restore(
                entity.getId(),
                lotPersistenceMapper.toDomain(entity.getLot()),
                invoicePersistenceMapper.toDomain(entity.getInvoice()),
                entity.getAllocatedQuantityLot(),
                entity.getAllocatedQuantityInvoice(),
                entity.getCreatedAt()
        );
    }
}
