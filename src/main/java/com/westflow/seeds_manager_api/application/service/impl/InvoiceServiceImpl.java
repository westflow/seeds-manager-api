package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.InvoiceService;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceService invoiceService;

    public InvoiceServiceImpl(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public Invoice register(Invoice invoice) {
        return invoiceService.register(invoice);
    }
}
