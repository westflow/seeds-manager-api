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
    public void saveAll(List<LotInvoice> lotInvoices) {
        List<LotInvoiceEntity> entities = lotInvoices.stream()
                .map(mapper::toEntity)
                .toList();
        jpaRepository.saveAll(entities);
    }
}
