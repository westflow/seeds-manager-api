package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.entity.LotInvoice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface LotInvoiceService {
    List<LotInvoice> createLotInvoices(Lot lot, List<Invoice> invoices, Map<Long, BigDecimal> allocationMap);
    List<LotInvoice> findAllByLotId(Long lotId);
}
