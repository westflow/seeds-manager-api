package com.westflow.seeds_manager_api.application.usecase.invoice;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceRequest;
import com.westflow.seeds_manager_api.application.usecase.seed.FindSeedByIdUseCase;
import com.westflow.seeds_manager_api.domain.exception.DuplicateInvoiceNumberException;
import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.model.Seed;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateInvoiceUseCase {

    private final InvoiceRepository invoiceRepository;
    private final FindInvoiceByIdUseCase findInvoiceByIdUseCase;
    private final FindSeedByIdUseCase findSeedByIdUseCase;

    public Invoice execute(Long id, InvoiceRequest request) {
        Invoice existing = findInvoiceByIdUseCase.execute(id);

        Seed seed = existing.getSeed();
        if (!seed.getId().equals(request.getSeedId())) {
            seed = findSeedByIdUseCase.execute(request.getSeedId());
        }

        if (!existing.getInvoiceNumber().equals(request.getInvoiceNumber())) {
            if (invoiceRepository.existsByInvoiceNumber(request.getInvoiceNumber())) {
                throw new DuplicateInvoiceNumberException(request.getInvoiceNumber());
            }
        }

        existing.update(
                request.getInvoiceNumber(),
                request.getProducerName(),
                seed,
                request.getTotalKg(),
                request.getOperationType(),
                request.getAuthNumber(),
                request.getCategory(),
                request.getPurity(),
                request.getHarvest(),
                request.getProductionState(),
                request.getPlantedArea(),
                request.getApprovedArea()
        );

        return invoiceRepository.save(existing);
    }
}
