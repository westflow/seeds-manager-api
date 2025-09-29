package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceCreateRequest;
import com.westflow.seeds_manager_api.api.mapper.InvoiceMapper;
import com.westflow.seeds_manager_api.application.service.InvoiceService;
import com.westflow.seeds_manager_api.application.service.SeedService;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.domain.exception.DuplicateInvoiceNumberException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaInvoiceRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.InvoiceEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.InvoiceSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final SeedService seedService;
    private final InvoiceMapper invoiceMapper;
    private final JpaInvoiceRepository jpaInvoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              SeedService seedService,
                              InvoiceMapper invoiceMapper,
                              JpaInvoiceRepository jpaInvoiceRepository) {
        this.invoiceRepository = invoiceRepository;
        this.seedService = seedService;
        this.invoiceMapper = invoiceMapper;
        this.jpaInvoiceRepository = jpaInvoiceRepository;
    }

    @Override
    public Invoice register(InvoiceCreateRequest request) {
        Long seedId = request.getSeedId();

        if (invoiceRepository.existsByInvoiceNumber(request.getInvoiceNumber())) {
            throw new DuplicateInvoiceNumberException(request.getInvoiceNumber());
        }

        Seed seed = seedService.findEntityById(seedId);

        Invoice invoice = invoiceMapper.toDomain(request, seed);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public void updateBalance(Invoice invoice, BigDecimal allocated) {
        invoice.withUpdatedBalance(allocated);
        invoiceRepository.save(invoice);
    }

    public void delete(Long id) {
        InvoiceEntity entity = jpaInvoiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Nota fiscal não encontrada."));
        if (!entity.isActive()) {
            throw new RuntimeException("Nota fiscal já está inativa.");
        }
        entity.setActive(false);
        jpaInvoiceRepository.save(entity);
    }

    public Page<InvoiceEntity> findAll(Pageable pageable) {
        Specification<InvoiceEntity> spec = InvoiceSpecifications.isActive();
        return jpaInvoiceRepository.findAll(spec, pageable);
    }
}
