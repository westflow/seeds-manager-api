package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.InvoiceEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.InvoicePersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaInvoiceRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.InvoiceSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    @Override
    public Optional<Invoice> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Invoice> findAll(Pageable pageable) {
        Specification<InvoiceEntity> spec = InvoiceSpecifications.isActive();
        return jpaRepository.findAll(spec, pageable).map(mapper::toDomain);
    }

    @Override
    public boolean existsByInvoiceNumber(String number) {
        return jpaRepository.existsByInvoiceNumber(number);
    }
}
