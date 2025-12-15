package com.westflow.seeds_manager_api.domain.service;

import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceAllocationService {

    private final InvoiceRepository invoiceRepository;

    public void applyAllocations(List<LotInvoice> allocations) {
        for (LotInvoice li : allocations) {
            li.getInvoice().withUpdatedBalance(
                    li.getAllocatedQuantityInvoice()
            );
            invoiceRepository.save(li.getInvoice());
        }
    }

    public void restoreAllocations(List<LotInvoice> allocations) {
        for (LotInvoice li : allocations) {
            li.getInvoice().restoreBalance(
                    li.getAllocatedQuantityInvoice()
            );
            invoiceRepository.save(li.getInvoice());
        }
    }
}
