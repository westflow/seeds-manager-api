package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceRequest;
import com.westflow.seeds_manager_api.api.dto.response.InvoiceResponse;
import com.westflow.seeds_manager_api.api.mapper.InvoiceMapper;
import com.westflow.seeds_manager_api.application.service.InvoiceService;
import com.westflow.seeds_manager_api.application.service.SeedService;
import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.model.Seed;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.DuplicateInvoiceNumberException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final SeedService seedService;
    private final InvoiceMapper invoiceMapper;

    @Override
    @Transactional
    public InvoiceResponse register(InvoiceRequest request) {
        if (invoiceRepository.existsByInvoiceNumber(request.getInvoiceNumber())) {
            throw new DuplicateInvoiceNumberException(request.getInvoiceNumber());
        }

        Seed seed = seedService.findEntityById(request.getSeedId())
                .orElseThrow(() -> new ResourceNotFoundException("Semente", request.getSeedId()));

        Invoice invoice = invoiceMapper.toDomain(request, seed);
        Invoice saved = invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse findById(Long id) {
        return invoiceMapper.toResponse(getInvoiceById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvoiceResponse> findAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable)
                .map(invoiceMapper::toResponse);
    }

    @Override
    @Transactional
    public InvoiceResponse update(Long id, InvoiceRequest request) {
        Invoice existing = getInvoiceById(id);

        if (!existing.getActive()) {
            throw new BusinessException("Nota fiscal está inativa e não pode ser atualizada.");
        }

        Seed seed = existing.getSeed();
        if (!seed.getId().equals(request.getSeedId())) {
            seed = seedService.findEntityById(request.getSeedId())
                    .orElseThrow(() -> new ResourceNotFoundException("Semente", request.getSeedId()));
        }

        Invoice updated = existing.toBuilder()
                .producerName(request.getProducerName())
                .totalKg(request.getTotalKg())
                .operationType(request.getOperationType())
                .authNumber(request.getAuthNumber())
                .category(request.getCategory())
                .purity(request.getPurity())
                .harvest(request.getHarvest())
                .productionState(request.getProductionState())
                .plantedArea(request.getPlantedArea())
                .approvedArea(request.getApprovedArea())
                .seed(seed)
                .updatedAt(LocalDateTime.now())
                .build();

        return invoiceMapper.toResponse(invoiceRepository.save(updated));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Invoice invoice = getInvoiceById(id);
        invoice.deactivate();
        invoiceRepository.save(invoice);
    }
    
    @Override
    public Optional<Invoice> findEntityById(Long id) {
        return invoiceRepository.findById(id);
    }
    
    @Override
    @Transactional
    public void updateBalance(Invoice invoice, BigDecimal allocated) {
        invoice.withUpdatedBalance(allocated);
        invoiceRepository.save(invoice);
    }

    @Override
    @Transactional
    public void restoreBalance(Invoice invoice, BigDecimal amount) {
        invoice.restoreBalance(amount);
        invoiceRepository.save(invoice);
    }

    private Invoice getInvoiceById(Long id) {
        return findEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota fiscal", id));
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
}
