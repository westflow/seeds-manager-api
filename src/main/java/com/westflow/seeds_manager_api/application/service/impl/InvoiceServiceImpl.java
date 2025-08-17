package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.InvoiceService;
import com.westflow.seeds_manager_api.application.service.SeedService;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.domain.exception.DuplicateInvoiceNumberException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final SeedService seedService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              SeedService seedService) {
        this.invoiceRepository = invoiceRepository;
        this.seedService = seedService;
    }

    @Override
    public Invoice register(Invoice invoice) {
        long seedId = invoice.getSeed().getId();

        if (invoiceRepository.existsByInvoiceNumber(invoice.getInvoiceNumber())) {
            throw new DuplicateInvoiceNumberException(invoice.getInvoiceNumber());
        }

        Seed seed = seedService.findById(seedId)
                .orElseThrow(() -> new ResourceNotFoundException("Semente", seedId));
        invoice.setSeed(seed);

        return invoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }
}
