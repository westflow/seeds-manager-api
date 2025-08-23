package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.LotCreateRequest;
import com.westflow.seeds_manager_api.api.mapper.LotMapper;
import com.westflow.seeds_manager_api.application.service.InvoiceService;
import com.westflow.seeds_manager_api.application.service.LotSequenceService;
import com.westflow.seeds_manager_api.application.service.LotService;
import com.westflow.seeds_manager_api.application.service.SeedService;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.domain.entity.User;
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

    public LotServiceImpl(LotRepository lotRepository,
                          SeedService seedService,
                          InvoiceService invoiceService,
                          LotMapper lotMapper,
                          LotSequenceService lotSequenceService) {
        this.lotRepository = lotRepository;
        this.seedService = seedService;
        this.invoiceService = invoiceService;
        this.lotMapper = lotMapper;
        this.lotSequenceService = lotSequenceService;
    }

    @Override
    public Lot register(LotCreateRequest request, User user) {
        Long seedId = request.getSeedId();
        Long invoiceId = request.getInvoiceId();

        Seed seed = seedService.findById(seedId)
                .orElseThrow(() -> new ResourceNotFoundException("Semente", seedId));

        Invoice invoice = invoiceService.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Nota fiscal", invoiceId));

        String lotNumber = lotSequenceService.generateNextFormattedNumber();

        Lot lot = lotMapper.toDomain(request, seed, invoice, user, lotNumber);
        return lotRepository.save(lot);
    }
}
