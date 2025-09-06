package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.LotInvoice;

import java.util.List;

public interface LotInvoiceRepository {
    void saveAll(List<LotInvoice> lotInvoices);
}
