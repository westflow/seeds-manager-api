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
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
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
    public LotResponse findById(Long id) {
        LotEntity entity = jpaLotRepository.findById(id)
                .filter(LotEntity::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Lote", id));

        return mapEntityToResponse(entity);
    }

    @Override
    public Optional<Lot> findEntityById(Long id) {
        return lotRepository.findById(id);
    }

    @Override
    public void updateBalance(Lot lot, BigDecimal allocated) {
        lot.withUpdatedBalance(allocated);
        lotRepository.save(lot);
    }

    private List<Invoice> fetchInvoices(Map<Long, BigDecimal> allocationMap) {
        return allocationMap.keySet().stream()
                .map(id -> invoiceService.findEntityById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Nota fiscal", id)))
                .toList();
    }

    private BagWeight fetchBagWeight(Long bagWeightId) {
        return bagWeightService.findEntityById(bagWeightId)
                .orElseThrow(() -> new ResourceNotFoundException("Tamanho da sacaria", bagWeightId));
    }

    private BagType fetchBagType(Long bagTypeId) {
        return bagTypeService.findEntityById(bagTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de sacaria", bagTypeId));
    }

    private Lab fetchLab(Long labId) {
        return labService.findEntityById(labId)
                .orElseThrow(() -> new ResourceNotFoundException("Laboratório", labId));
    }

    @Override
    public void delete(Long id) {
        LotEntity entity = jpaLotRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Lote não encontrado."));
        if (!entity.isActive()) {
            throw new RuntimeException("Lote já está inativo.");
        }
        entity.setActive(false);
        jpaLotRepository.save(entity);
    }

    @Override
    public Page<LotResponse> findAll(Pageable pageable) {
        Specification<LotEntity> spec = LotSpecifications.isActive();
        Page<LotEntity> page = jpaLotRepository.findAll(spec, pageable);
        return page.map(this::mapEntityToResponse);
    }

    private LotResponse mapEntityToResponse(LotEntity entity) {
        List<InvoiceAllocationResponse> allocationResponses = entity.getLotInvoices() == null
                ? List.of()
                : entity.getLotInvoices().stream()
                    .map(li -> InvoiceAllocationResponse.builder()
                            .invoiceId(li.getInvoice().getId())
                            .quantity(li.getAllocatedQuantityLot())
                            .build())
                    .toList();

        return LotResponse.builder()
                .id(entity.getId())
                .lotNumber(entity.getLotNumber())
                .lotType(entity.getLotType())
                .seedType(entity.getSeedType())
                .category(entity.getCategory())
                .bagWeightId(entity.getBagWeight() != null ? entity.getBagWeight().getId() : null)
                .bagTypeId(entity.getBagType() != null ? entity.getBagType().getId() : null)
                .quantityTotal(entity.getQuantityTotal())
                .balance(entity.getBalance())
                .productionOrder(entity.getProductionOrder())
                .analysisBulletin(entity.getAnalysisBulletin())
                .bulletinDate(entity.getBulletinDate())
                .hardSeeds(entity.getHardSeeds())
                .wildSeeds(entity.getWildSeeds())
                .otherCultivatedSpecies(entity.getOtherCultivatedSpecies())
                .tolerated(entity.getTolerated())
                .prohibited(entity.getProhibited())
                .labId(entity.getLab() != null ? entity.getLab().getId() : null)
                .invoiceAllocations(allocationResponses)
                .validityDate(entity.getValidityDate())
                .seedScore(entity.getSeedScore())
                .purity(entity.getPurity())
                .build();
    }
}
