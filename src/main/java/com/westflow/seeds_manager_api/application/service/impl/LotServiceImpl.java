package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceAllocationRequest;
import com.westflow.seeds_manager_api.api.dto.request.LotCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.InvoiceAllocationResponse;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.api.mapper.LotMapper;
import com.westflow.seeds_manager_api.application.service.*;
import com.westflow.seeds_manager_api.domain.entity.*;
import com.westflow.seeds_manager_api.domain.enums.OperationType;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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

    public LotServiceImpl(LotRepository lotRepository,
                          InvoiceService invoiceService,
                          LotMapper lotMapper,
                          LotSequenceService lotSequenceService,
                          BagWeightService bagWeightService,
                          BagTypeService bagTypeService,
                          LabService labService,
                          LotInvoiceService lotInvoiceService) {
        this.lotRepository = lotRepository;
        this.invoiceService = invoiceService;
        this.lotMapper = lotMapper;
        this.lotSequenceService = lotSequenceService;
        this.bagWeightService = bagWeightService;
        this.bagTypeService = bagTypeService;
        this.labService = labService;
        this.lotInvoiceService = lotInvoiceService;
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

        List<Invoice> invoices = allocationMap.keySet().stream()
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
        validateInvoices(invoices, request, allocationMap);

        String lotNumber = lotSequenceService.generateNextFormattedNumber();

        Lot lot = lotMapper.toDomain(request, invoices, bagWeight, bagType, lab, user, lotNumber);
        Lot savedLot = lotRepository.save(lot);
        List<LotInvoice> savedLotInvoices = lotInvoiceService.createLotInvoices(savedLot, invoices, allocationMap);
        List<InvoiceAllocationResponse> allocationResponses = savedLotInvoices.stream()
                .map(li -> InvoiceAllocationResponse.builder()
                        .invoiceId(li.getInvoice().getId())
                        .quantity(li.getAllocatedQuantity())
                        .build())
                .toList();

        LotResponse response = lotMapper.toResponse(savedLot);
        response.setInvoiceAllocations(allocationResponses);
        return response;
    }

    private void validateInvoices(List<Invoice> invoices, LotCreateRequest request, Map<Long, BigDecimal> allocationMap) {
        if (invoices.isEmpty()) {
            throw new ValidationException("É necessário informar ao menos uma nota fiscal.");
        }

        if (invoices.size() > 1 &&
                invoices.stream().anyMatch(i -> i.getOperationType() == OperationType.REPACKAGING)
        ) {
            throw new BusinessException("Notas fiscais com o tipo de operação reembalo devem conter exatamente uma nota fiscal.");
        }

        boolean sameSeed  = invoices.stream()
                .map(i -> i.getSeed().getId())
                .distinct()
                .count() == 1;

        if (!sameSeed ) {
            throw new BusinessException("Todas as notas fiscais devem conter a mesma semente.");
        }

        boolean sameHarvest = invoices.stream()
                .map(Invoice::getHarvest)
                .distinct()
                .count() == 1;

        if (!sameHarvest) {
            throw new BusinessException("Todas as notas fiscais devem pertencer à mesma safra.");
        }

        if (invoices.stream().anyMatch(i ->
                i.getOperationType() == OperationType.REPACKAGING &&
                        i.getPurity().compareTo(request.getPurity()) != 0
        )) {
            throw new BusinessException("Notas com tipo de operação de reembalo: a pureza deve ser a mesma da nota fiscal.");
        }

        BigDecimal totalAllocated = allocationMap.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalAllocated.compareTo(request.getQuantityTotal()) != 0) {
            throw new BusinessException("A soma das quantidades alocadas não corresponde à quantidade total do lote.");
        }

        for (Invoice invoice : invoices) {
            BigDecimal allocated = allocationMap.get(invoice.getId());
            if (allocated.compareTo(invoice.getBalance()) > 0) {
                throw new BusinessException("Saldo insuficiente na nota fiscal " + invoice.getId());
            }
        }
    }
}
