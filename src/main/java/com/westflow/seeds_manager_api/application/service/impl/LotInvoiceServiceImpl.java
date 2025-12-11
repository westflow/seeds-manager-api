package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.InvoiceService;
import com.westflow.seeds_manager_api.application.service.LotInvoiceService;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.entity.LotInvoice;
import com.westflow.seeds_manager_api.domain.repository.LotInvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class LotInvoiceServiceImpl implements LotInvoiceService {

    private final LotInvoiceRepository lotInvoiceRepository;
    private final InvoiceService invoiceService;

    @Override
    @Transactional
    public List<LotInvoice> createLotInvoices(Lot lot, List<Invoice> invoices, Map<Long, BigDecimal> allocationMap) {
        List<LotInvoice> lotInvoices = new ArrayList<>();

        for (Invoice invoice : invoices) {
            BigDecimal quantityLot = allocationMap.get(invoice.getId());
            if (quantityLot == null) {
                throw new ValidationException("Alocação ausente para a nota fiscal com id " + invoice.getId());
            }
            BigDecimal quantityInvoice = calculateAllocatedQuantityInvoice(lot.getPurity(), invoice.getPurity(), quantityLot);
            lotInvoices.add(new LotInvoice(
                    null,
                    lot,
                    invoice,
                    quantityLot,
                    quantityInvoice,
                    LocalDateTime.now()
            ));
            invoiceService.updateBalance(invoice, quantityInvoice);
        }

        return lotInvoiceRepository.saveAll(lotInvoices);
    }

    @Override
    public List<LotInvoice> findAllByLotId(Long lotId) {
        return lotInvoiceRepository.findAllByLotId(lotId);
    }

    private BigDecimal calculateAllocatedQuantityInvoice(BigDecimal lotPurity, BigDecimal invoicePurity, BigDecimal allocatedQuantityLot) {
        return allocatedQuantityLot.multiply(lotPurity).divide(invoicePurity, 2, RoundingMode.HALF_UP);
    }
}
