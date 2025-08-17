package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.Invoice;

import java.util.Optional;

public interface InvoiceService {
    Invoice register(Invoice invoice);
    Optional<Invoice> findById(Long id);
}
