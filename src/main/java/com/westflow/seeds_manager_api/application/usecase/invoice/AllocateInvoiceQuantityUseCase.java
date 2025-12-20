package com.westflow.seeds_manager_api.application.usecase.invoice;

import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AllocateInvoiceQuantityUseCase {

    private final InvoiceRepository invoiceRepository;
    private final FindInvoiceByIdUseCase findInvoiceByIdUseCase;

    public void execute(Long invoiceId, BigDecimal allocated) {
        Invoice invoice = findInvoiceByIdUseCase.execute(invoiceId);
        invoice.withUpdatedBalance(allocated);
        invoiceRepository.save(invoice);
    }
}
