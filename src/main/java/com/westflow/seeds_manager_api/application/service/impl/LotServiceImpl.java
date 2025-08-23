package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.LotCreateRequest;
import com.westflow.seeds_manager_api.api.mapper.LotMapper;
import com.westflow.seeds_manager_api.application.service.*;
import com.westflow.seeds_manager_api.domain.entity.*;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import org.springframework.stereotype.Service;

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
        Long invoiceId = request.getInvoiceId();
        Long bagWeightId = request.getBagWeightId();
        Long bagTypeId = request.getBagTypeId();
        Long labId = request.getLabId();

        Seed seed = seedService.findById(seedId)
                .orElseThrow(() -> new ResourceNotFoundException("Semente", seedId));

        Invoice invoice = invoiceService.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Nota fiscal", invoiceId));

        BagWeight bagWeight = bagWeightService.findById(bagWeightId)
                .orElseThrow(() -> new ResourceNotFoundException("Tamanho da sacaria", bagWeightId));

        BagType bagType = bagTypeService.findById(bagTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo da sacaria", bagTypeId));

        Lab lab = null;
        if (labId != null) {
            lab = labService.findById(labId)
                    .orElseThrow(() -> new ResourceNotFoundException("Laboratório", labId));
        }

        String lotNumber = lotSequenceService.generateNextFormattedNumber();

        Lot lot = lotMapper.toDomain(request, seed, invoice, bagWeight, bagType, lab, user, lotNumber);
        return lotRepository.save(lot);
    }
}
