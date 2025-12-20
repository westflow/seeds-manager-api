package com.westflow.seeds_manager_api.application.usecase.invoice;

import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteInvoiceUseCase {

    private final InvoiceRepository invoiceRepository;
    private final FindInvoiceByIdUseCase findInvoiceByIdUseCase;

    public void execute(Long id) {
        Invoice invoice = findInvoiceByIdUseCase.execute(id);
        invoice.deactivate();
        invoiceRepository.save(invoice);
    }
}
