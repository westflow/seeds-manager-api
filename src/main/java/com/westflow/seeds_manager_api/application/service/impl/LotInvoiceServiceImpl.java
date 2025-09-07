package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.LotInvoiceService;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.entity.LotInvoice;
import com.westflow.seeds_manager_api.domain.repository.LotInvoiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LotInvoiceServiceImpl implements LotInvoiceService {

    private final LotInvoiceRepository lotInvoiceRepository;

    public LotInvoiceServiceImpl(LotInvoiceRepository lotInvoiceRepository) {
        this.lotInvoiceRepository = lotInvoiceRepository;
    }

    @Override
    public List<LotInvoice> createLotInvoices(Lot lot, List<Invoice> invoices, Map<Long, BigDecimal> allocationMap) {
        List<LotInvoice> lotInvoices = new ArrayList<>();

        for (Invoice invoice : invoices) {
            BigDecimal quantity = allocationMap.get(invoice.getId());

            lotInvoices.add(new LotInvoice(
                    null,
                    lot,
                    invoice,
                    quantity,
                    null
            ));
        }

        return lotInvoiceRepository.saveAll(lotInvoices);
    }
}
