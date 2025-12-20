package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.LotInvoiceService;
import com.westflow.seeds_manager_api.application.usecase.invoice.AllocateInvoiceQuantityUseCase;
import com.westflow.seeds_manager_api.application.usecase.invoice.FindInvoiceByIdUseCase;
import com.westflow.seeds_manager_api.application.usecase.invoice.RestoreInvoiceBalanceUseCase;
import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import com.westflow.seeds_manager_api.domain.repository.LotInvoiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final AllocateInvoiceQuantityUseCase allocateInvoiceQuantityUseCase;
    private final RestoreInvoiceBalanceUseCase restoreInvoiceBalanceUseCase;
    private final FindInvoiceByIdUseCase findInvoiceByIdUseCase;

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
            allocateInvoiceQuantityUseCase.execute(invoice.getId(), quantityInvoice);
        }

        return lotInvoiceRepository.saveAll(lotInvoices);
    }

    @Override
    public List<LotInvoice> findAllByLotId(Long lotId) {
        return lotInvoiceRepository.findAllByLotId(lotId);
    }

    @Override
    @Transactional
    public List<LotInvoice> updateLotInvoices(Lot lot, List<Invoice> invoices, Map<Long, BigDecimal> allocationMap) {
        List<LotInvoice> currentLotInvoices = lotInvoiceRepository.findAllByLotId(lot.getId());

        for (LotInvoice lotInvoice : currentLotInvoices) {
            Invoice invoice = lotInvoice.getInvoice();
            restoreInvoiceBalanceUseCase.execute(invoice.getId(), lotInvoice.getAllocatedQuantityInvoice());
        }

        lotInvoiceRepository.deleteAllByLotId(lot.getId());

        List<Invoice> refreshedInvoices = new ArrayList<>();
        for (Invoice invoice : invoices) {
            Invoice refreshed = findInvoiceByIdUseCase.execute(invoice.getId());
            refreshedInvoices.add(refreshed);
        }

        return createLotInvoices(lot, refreshedInvoices, allocationMap);
    }

    private BigDecimal calculateAllocatedQuantityInvoice(BigDecimal lotPurity, BigDecimal invoicePurity, BigDecimal allocatedQuantityLot) {
        return allocatedQuantityLot.multiply(lotPurity).divide(invoicePurity, 2, RoundingMode.HALF_UP);
    }
}
