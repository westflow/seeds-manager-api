package com.westflow.seeds_manager_api.application.usecase.invoice;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceRequest;
import com.westflow.seeds_manager_api.domain.exception.DuplicateInvoiceNumberException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.model.Seed;
import com.westflow.seeds_manager_api.domain.repository.InvoiceRepository;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateInvoiceUseCase {

    private final InvoiceRepository invoiceRepository;
    private final SeedRepository seedRepository;
    private final FindInvoiceByIdUseCase findInvoiceByIdUseCase;

    public Invoice execute(Long id, InvoiceRequest request) {
        Invoice existing = findInvoiceByIdUseCase.execute(id);

        Seed seed = existing.getSeed();
        if (!seed.getId().equals(request.getSeedId())) {
            seed = seedRepository.findById(request.getSeedId())
                    .orElseThrow(() -> new ResourceNotFoundException("Semente", request.getSeedId()));
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
