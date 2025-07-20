package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.InvoiceEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.InvoicePersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaInvoiceRepository;
import org.springframework.stereotype.Component;

@Component
public class InvoiceRepositoryAdapter implements InvoiceRepository {
    private final JpaInvoiceRepository jpaRepository;
    private final InvoicePersistenceMapper mapper;

    public InvoiceRepositoryAdapter(JpaInvoiceRepository jpaRepository, InvoicePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Invoice save(Invoice invoice) {
        InvoiceEntity entity = mapper.toEntity(invoice);
        return mapper.toDomain(jpaRepository.save(entity));
    }
}
