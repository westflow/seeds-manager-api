package com.westflow.seeds_manager_api.application.usecase.invoice;

import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class RestoreInvoiceBalanceUseCase {

    private final InvoiceRepository invoiceRepository;
    private final FindInvoiceByIdUseCase findInvoiceByIdUseCase;

    public void execute(Long invoiceId, BigDecimal amount) {
        Invoice invoice = findInvoiceByIdUseCase.execute(invoiceId);
        invoice.restoreBalance(amount);
        invoiceRepository.save(invoice);
    }
}
