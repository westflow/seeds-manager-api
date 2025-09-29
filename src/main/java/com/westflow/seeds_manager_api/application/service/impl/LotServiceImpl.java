package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceAllocationRequest;
import com.westflow.seeds_manager_api.api.dto.request.LotCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.InvoiceAllocationResponse;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.api.mapper.LotMapper;
import com.westflow.seeds_manager_api.application.service.*;
import com.westflow.seeds_manager_api.application.validation.LotValidator;
import com.westflow.seeds_manager_api.domain.entity.*;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaLotRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.LotSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    private final InvoiceService invoiceService;
    private final LotMapper lotMapper;
    private final LotSequenceService lotSequenceService;
    private final BagWeightService bagWeightService;
    private final BagTypeService bagTypeService;
    private final LabService labService;
    private final LotInvoiceService lotInvoiceService;

    private final LotValidator lotValidator;
    private final JpaLotRepository jpaLotRepository;

    public LotServiceImpl(LotRepository lotRepository,
                          InvoiceService invoiceService,
                          LotMapper lotMapper,
                          LotSequenceService lotSequenceService,
                          BagWeightService bagWeightService,
                          BagTypeService bagTypeService,
                          LabService labService,
                          LotInvoiceService lotInvoiceService,
                          LotValidator lotValidator,
                          JpaLotRepository jpaLotRepository) {
        this.lotRepository = lotRepository;
        this.invoiceService = invoiceService;
        this.lotMapper = lotMapper;
        this.lotSequenceService = lotSequenceService;
        this.bagWeightService = bagWeightService;
        this.bagTypeService = bagTypeService;
        this.labService = labService;
        this.lotInvoiceService = lotInvoiceService;
        this.lotValidator = lotValidator;
        this.jpaLotRepository = jpaLotRepository;
    }

    @Override
    @Transactional
    public LotResponse register(LotCreateRequest request, User user) {
        Long bagWeightId = request.getBagWeightId();
        Long bagTypeId = request.getBagTypeId();
        Long labId = request.getLabId();
        List<InvoiceAllocationRequest> allocationRequests = request.getInvoiceAllocations();

        Map<Long, BigDecimal> allocationMap = allocationRequests.stream()
                .collect(Collectors.toMap(InvoiceAllocationRequest::getInvoiceId, InvoiceAllocationRequest::getQuantity));

        List<Invoice> invoices = fetchInvoices(allocationMap);
        BagWeight bagWeight = fetchBagWeight(bagWeightId);
        BagType bagType = fetchBagType(bagTypeId);
        Lab lab = (labId != null && labId > 0) ? fetchLab(labId) : null;

        lotValidator.validateInvoices(invoices, request, allocationMap);

        String lotNumber = lotSequenceService.generateNextFormattedNumber();
        Lot lot = lotMapper.toDomain(request, invoices, bagWeight, bagType, lab, user, lotNumber);
        Lot savedLot = lotRepository.save(lot);

        List<LotInvoice> savedLotInvoices = lotInvoiceService.createLotInvoices(savedLot, invoices, allocationMap);

        List<InvoiceAllocationResponse> allocationResponses = savedLotInvoices.stream()
                .map(li -> InvoiceAllocationResponse.builder()
                        .invoiceId(li.getInvoice().getId())
                        .quantity(li.getAllocatedQuantityLot())
                        .build())
                .toList();

        LotResponse response = lotMapper.toResponse(savedLot);
        response.setInvoiceAllocations(allocationResponses);
        return response;
    }

    @Override
    public Optional<Lot> findById(Long id) {
        return lotRepository.findById(id);
    }

    @Override
    public void updateBalance(Lot lot, BigDecimal allocated) {
        lot.withUpdatedBalance(allocated);
        lotRepository.save(lot);
    }

    private List<Invoice> fetchInvoices(Map<Long, BigDecimal> allocationMap) {
        return allocationMap.keySet().stream()
                .map(id -> invoiceService.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Nota fiscal", id)))
                .toList();
    }

    private BagWeight fetchBagWeight(Long bagWeightId) {
        return bagWeightService.findById(bagWeightId)
                .orElseThrow(() -> new ResourceNotFoundException("Tamanho da sacaria", bagWeightId));
    }

    private BagType fetchBagType(Long bagTypeId) {
        return bagTypeService.findEntityById(bagTypeId);
    }

    private Lab fetchLab(Long labId) {
        return labService.findById(labId)
                .orElseThrow(() -> new ResourceNotFoundException("Laboratório", labId));
    }

    public void delete(Long id) {
        LotEntity entity = jpaLotRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Lote não encontrado."));
        if (!entity.isActive()) {
            throw new RuntimeException("Lote já está inativo.");
        }
        entity.setActive(false);
        jpaLotRepository.save(entity);
    }

    public Page<LotEntity> findAll(Pageable pageable) {
        Specification<LotEntity> spec = LotSpecifications.isActive();
        return jpaLotRepository.findAll(spec, pageable);
    }
}
