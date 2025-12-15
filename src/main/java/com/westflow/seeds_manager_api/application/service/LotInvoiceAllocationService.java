package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import com.westflow.seeds_manager_api.domain.repository.LotInvoiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LotInvoiceAllocationService {

    private final LotInvoiceRepository lotInvoiceRepository;
    private final InvoiceService invoiceService;

    @Transactional
    public List<LotInvoice> allocate(
            Lot lot,
            List<Invoice> invoices,
            Map<Long, BigDecimal> allocationMap
    ) {

        List<LotInvoice> allocations = new ArrayList<>();

        for (Invoice invoice : invoices) {

            BigDecimal quantityLot = allocationMap.get(invoice.getId());

            if (quantityLot == null) {
                throw new ValidationException(
                        "Alocação ausente para a nota fiscal " + invoice.getId()
                );
            }

            BigDecimal quantityInvoice =
                    calculateInvoiceQuantity(lot.getPurity(), invoice.getPurity(), quantityLot);

            lot.decreaseBalance(quantityLot);
            invoiceService.updateBalance(invoice, quantityInvoice);

            allocations.add(
                    LotInvoice.create(lot, invoice, quantityLot, quantityInvoice)
            );
        }

        return lotInvoiceRepository.saveAll(allocations);
    }

    private BigDecimal calculateInvoiceQuantity(
            BigDecimal lotPurity,
            BigDecimal invoicePurity,
            BigDecimal quantityLot
    ) {
        return quantityLot.multiply(lotPurity)
                .divide(invoicePurity, 2, RoundingMode.HALF_UP);
    }
}
