package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.InvoiceService;
import com.westflow.seeds_manager_api.application.service.LotService;
import com.westflow.seeds_manager_api.application.service.SeedService;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import org.springframework.stereotype.Service;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    private final SeedService seedService;
    private final InvoiceService invoiceService;

    public LotServiceImpl(LotRepository lotRepository,
                          SeedService seedService,
                          InvoiceService invoiceService) {
        this.lotRepository = lotRepository;
        this.seedService = seedService;
        this.invoiceService = invoiceService;
    }

    @Override
    public Lot register(Lot lot) {
        Long seedId = lot.getSeed().getId();
        Long invoiceId = lot.getInvoice().getId();

        Seed seed = seedService.findById(seedId)
                .orElseThrow(() -> new ResourceNotFoundException("Semente", seedId));

        Invoice invoice = invoiceService.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Nota fiscal", invoiceId));

        lot.setSeed(seed);
        lot.setInvoice(invoice);

        return lotRepository.save(lot);
    }
}
