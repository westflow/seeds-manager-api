package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.entity.LotInvoice;
import com.westflow.seeds_manager_api.domain.repository.LotInvoiceRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotInvoiceEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.LotInvoicePersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaLotInvoiceRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LotInvoiceRepositoryAdapter implements LotInvoiceRepository {

    private final JpaLotInvoiceRepository jpaRepository;
    private final LotInvoicePersistenceMapper mapper;

    public LotInvoiceRepositoryAdapter(JpaLotInvoiceRepository jpaRepository,
                                       LotInvoicePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<LotInvoice> saveAll(List<LotInvoice> lotInvoices) {
        List<LotInvoice> saved = new java.util.ArrayList<>();
        for (LotInvoice lotInvoice : lotInvoices) {
            LotInvoiceEntity entity = mapper.toEntity(lotInvoice);
            LotInvoiceEntity savedEntity = jpaRepository.save(entity);
            saved.add(mapper.toDomain(savedEntity));
        }
        return saved;
    }
}
