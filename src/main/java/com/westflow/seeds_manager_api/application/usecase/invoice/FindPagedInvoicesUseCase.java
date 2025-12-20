package com.westflow.seeds_manager_api.application.usecase.invoice;

import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPagedInvoicesUseCase {

    private final InvoiceRepository invoiceRepository;

    public Page<Invoice> execute(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }
}
