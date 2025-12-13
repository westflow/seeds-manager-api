package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.LotInvoice;

import java.util.List;

public interface LotInvoiceRepository {
    List<LotInvoice> saveAll(List<LotInvoice> lotInvoices);
    List<LotInvoice> findAllByLotId(Long lotId);
    void deleteAllByLotId(Long lotId);
}
