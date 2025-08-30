package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.LotCreateRequest;
import com.westflow.seeds_manager_api.api.mapper.LotMapper;
import com.westflow.seeds_manager_api.application.service.*;
import com.westflow.seeds_manager_api.domain.entity.*;
import com.westflow.seeds_manager_api.domain.enums.LotType;
import com.westflow.seeds_manager_api.domain.enums.OperationType;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    private final SeedService seedService;
    private final InvoiceService invoiceService;
    private final LotMapper lotMapper;
    private final LotSequenceService lotSequenceService;
    private final BagWeightService bagWeightService;
    private final BagTypeService bagTypeService;
    private final LabService labService;

    public LotServiceImpl(LotRepository lotRepository,
                          SeedService seedService,
                          InvoiceService invoiceService,
                          LotMapper lotMapper,
                          LotSequenceService lotSequenceService,
                          BagWeightService bagWeightService,
                          BagTypeService bagTypeService,
                          LabService labService) {
        this.lotRepository = lotRepository;
        this.seedService = seedService;
        this.invoiceService = invoiceService;
        this.lotMapper = lotMapper;
        this.lotSequenceService = lotSequenceService;
        this.bagWeightService = bagWeightService;
        this.bagTypeService = bagTypeService;
        this.labService = labService;
    }

    @Override
    public Lot register(LotCreateRequest request, User user) {
        Long seedId = request.getSeedId();
        Long bagWeightId = request.getBagWeightId();
        Long bagTypeId = request.getBagTypeId();
        Long labId = request.getLabId();
        List<Long> invoiceIds = request.getInvoiceIds();

        Seed seed = seedService.findById(seedId)
                .orElseThrow(() -> new ResourceNotFoundException("Semente", seedId));

        List<Invoice> invoices = invoiceIds.stream()
                .map(id -> invoiceService.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Nota fiscal", id)))
                .toList();

        BagWeight bagWeight = bagWeightService.findById(bagWeightId)
                .orElseThrow(() -> new ResourceNotFoundException("Tamanho da sacaria", bagWeightId));

        BagType bagType = bagTypeService.findById(bagTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo da sacaria", bagTypeId));

        Lab lab = null;
        if (labId != null) {
            lab = labService.findById(labId)
                    .orElseThrow(() -> new ResourceNotFoundException("Laboratório", labId));
        }
        validateInvoices(invoices, request);

        String lotNumber = lotSequenceService.generateNextFormattedNumber();

        Lot lot = lotMapper.toDomain(request, seed, invoices, bagWeight, bagType, lab, user, lotNumber);
        return lotRepository.save(lot);
    }

    private void validateInvoices(List<Invoice> invoices, LotCreateRequest request) {
        if (invoices.isEmpty()) {
            throw new ValidationException("É necessário informar ao menos uma nota fiscal.");
        }

        if (invoices.size() > 1 &&
                invoices.stream().anyMatch(i -> i.getOperationType() == OperationType.REPACKAGING)
        ) {
            throw new BusinessException("Notas fiscais com o tipo de operação reembalo devem conter exatamente uma nota fiscal.");
        }

        boolean mesmaSafra = invoices.stream()
                .map(Invoice::getHarvest)
                .distinct()
                .count() == 1;

        if (!mesmaSafra) {
            throw new BusinessException("Todas as notas fiscais devem pertencer à mesma safra.");
        }

        if (invoices.stream().anyMatch(i -> i.getOperationType() == OperationType.REPACKAGING &&
                Objects.equals(i.getPurity(), request.getPurity()))) {
            throw new BusinessException("Notas com tipo de operação de reembalo a pureza deve ser a mesma da nota fiscal.");
        }
    }
}
