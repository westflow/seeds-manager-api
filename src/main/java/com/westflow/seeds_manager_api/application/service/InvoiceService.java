package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceRequest;
import com.westflow.seeds_manager_api.api.dto.response.InvoiceResponse;
import com.westflow.seeds_manager_api.domain.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

public interface InvoiceService {
    InvoiceResponse register(InvoiceRequest request);
    InvoiceResponse findById(Long id);
    Optional<Invoice> findEntityById(Long id);
    Page<InvoiceResponse> findAll(Pageable pageable);
    InvoiceResponse update(Long id, InvoiceRequest request);
    void delete(Long id);
    void updateBalance(Invoice invoice, BigDecimal allocated);
    void restoreBalance(Invoice invoice, BigDecimal amount);
}
