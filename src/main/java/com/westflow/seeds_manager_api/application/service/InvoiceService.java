package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceCreateRequest;
import com.westflow.seeds_manager_api.domain.entity.Invoice;

import java.util.Optional;

public interface InvoiceService {
    Invoice register(InvoiceCreateRequest request);
    Optional<Invoice> findById(Long id);
}
