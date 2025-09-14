package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.InvoiceService;
import com.westflow.seeds_manager_api.application.service.LotInvoiceService;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.entity.LotInvoice;
import com.westflow.seeds_manager_api.domain.repository.LotInvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class LotInvoiceServiceImpl implements LotInvoiceService {

    private final LotInvoiceRepository lotInvoiceRepository;
    private final InvoiceService invoiceService;

    @Override
    public List<LotInvoice> createLotInvoices(Lot lot, List<Invoice> invoices, Map<Long, BigDecimal> allocationMap) {
        List<LotInvoice> lotInvoices = new ArrayList<>();

        for (Invoice invoice : invoices) {
            BigDecimal quantityLot = allocationMap.get(invoice.getId());
            BigDecimal quantityInvoice = calculateAllocatedQuantityInvoice(lot.getPurity(), invoice.getPurity(), quantityLot);
            lotInvoices.add(new LotInvoice(
                    null,
                    lot,
                    invoice,
                    quantityLot,
                    quantityInvoice,
                    null
            ));
            invoiceService.updateBalance(invoice, quantityInvoice);
        }

        return lotInvoiceRepository.saveAll(lotInvoices);
    }

    private BigDecimal calculateAllocatedQuantityInvoice(BigDecimal lotPurity, BigDecimal invoicePurity, BigDecimal allocatedQuantityLot) {
        return allocatedQuantityLot.multiply(lotPurity).divide(invoicePurity, 2, RoundingMode.HALF_UP);
    }
}
