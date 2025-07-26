package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.InvoiceService;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice register(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
}
