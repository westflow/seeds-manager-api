package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceAllocationRequest;
import com.westflow.seeds_manager_api.api.dto.request.LotRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.api.mapper.LotMapper;
import com.westflow.seeds_manager_api.application.service.*;
import com.westflow.seeds_manager_api.application.validation.LotValidator;
import com.westflow.seeds_manager_api.domain.entity.*;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import com.westflow.seeds_manager_api.domain.repository.LotReservationRepository;
import com.westflow.seeds_manager_api.domain.repository.LotWithdrawalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final LotWithdrawalRepository lotWithdrawalRepository;
    private final LotReservationRepository lotReservationRepository;

    @Override
    @Transactional
    public LotResponse register(LotRequest request, User user) {
        Long bagWeightId = request.getBagWeightId();
        Long bagTypeId = request.getBagTypeId();
        Long labId = request.getLabId();
        List<InvoiceAllocationRequest> allocationRequests = request.getInvoiceAllocations() != null ? request.getInvoiceAllocations() : List.of();

        Map<Long, BigDecimal> allocationMap = allocationRequests.stream()
                .filter(req -> req.getInvoiceId() != null && req.getQuantity() != null)
                .collect(Collectors.toMap(InvoiceAllocationRequest::getInvoiceId, InvoiceAllocationRequest::getQuantity, BigDecimal::add));

        List<Invoice> invoices = fetchInvoices(allocationMap);
        BagWeight bagWeight = fetchBagWeight(bagWeightId);
        BagType bagType = fetchBagType(bagTypeId);
        Lab lab = (labId != null && labId > 0) ? fetchLab(labId) : null;

        lotValidator.validateInvoices(invoices, request, allocationMap);

        String lotNumber = lotSequenceService.generateNextFormattedNumber();
        Lot lot = lotMapper.toDomain(request, bagWeight, bagType, lab, user, lotNumber);
        Lot savedLot = lotRepository.save(lot);

        List<LotInvoice> savedLotInvoices = lotInvoiceService.createLotInvoices(savedLot, invoices, allocationMap);

        LotResponse response = lotMapper.toResponse(savedLot);
        response.setInvoiceAllocations(lotMapper.toInvoiceAllocations(savedLotInvoices));
        return response;
    }

    @Override
    @Transactional
    public LotResponse update(Long id, LotRequest request, User user) {
        Lot existingLot = getLotById(id);

        boolean hasWithdrawals = lotWithdrawalRepository.existsByLotId(id);
        boolean hasReservations = lotReservationRepository.existsByLotId(id);

        if (hasWithdrawals || hasReservations) {
            throw new BusinessException(
                    "Não é permitido alterar o lote pois já existem saídas ou reservas associadas.");
        }

        Long bagWeightId = request.getBagWeightId();
        Long bagTypeId = request.getBagTypeId();
        Long labId = request.getLabId();

        List<InvoiceAllocationRequest> allocationRequests =
                request.getInvoiceAllocations() != null ? request.getInvoiceAllocations() : List.of();

        Map<Long, BigDecimal> allocationMap = allocationRequests.stream()
                .filter(req -> req.getInvoiceId() != null && req.getQuantity() != null)
                .collect(Collectors.toMap(InvoiceAllocationRequest::getInvoiceId,
                        InvoiceAllocationRequest::getQuantity, BigDecimal::add));

        List<Invoice> invoices = fetchInvoices(allocationMap);
        BagWeight bagWeight = fetchBagWeight(bagWeightId);
        BagType bagType = fetchBagType(bagTypeId);
        Lab lab = (labId != null && labId > 0) ? fetchLab(labId) : null;

        lotValidator.validateInvoices(invoices, request, allocationMap);

        Lot updatedLot = lotMapper.toUpdatedDomain(existingLot, request, bagWeight, bagType, lab);
        Lot savedLot = lotRepository.save(updatedLot);

        List<LotInvoice> lotInvoices = lotInvoiceService.updateLotInvoices(savedLot, invoices, allocationMap);

        LotResponse response = lotMapper.toResponse(savedLot);
        response.setInvoiceAllocations(lotMapper.toInvoiceAllocations(lotInvoices));
        return response;
    }

    @Override
    public LotResponse findById(Long id) {
        Lot lot = getLotById(id);

        List<LotInvoice> lotInvoices = lotInvoiceService.findAllByLotId(id);

        LotResponse response = lotMapper.toResponse(lot);
        response.setInvoiceAllocations(lotMapper.toInvoiceAllocations(lotInvoices));

        return response;
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

    @Override
    public void delete(Long id) {
        Lot lot = getLotById(id);
        lot.deactivate();
        lotRepository.save(lot);
    }

    @Override
    public Page<LotResponse> findAll(Pageable pageable) {
        return lotRepository.findAll(pageable)
                .map(lot -> {
                    List<LotInvoice> lotInvoices = lotInvoiceService.findAllByLotId(lot.getId());
                    LotResponse response = lotMapper.toResponse(lot);
                    response.setInvoiceAllocations(lotMapper.toInvoiceAllocations(lotInvoices));
                    return response;
                });
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

    private Lot getLotById(Long id) {
        return findEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote", id));
    }
}
