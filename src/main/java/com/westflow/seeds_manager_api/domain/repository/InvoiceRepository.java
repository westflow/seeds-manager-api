package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.Invoice;

public interface InvoiceRepository {
    Invoice save(Invoice invoice);
}
