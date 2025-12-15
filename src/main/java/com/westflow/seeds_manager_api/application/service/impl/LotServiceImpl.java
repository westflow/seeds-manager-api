package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.api.mapper.LotMapper;
import com.westflow.seeds_manager_api.application.service.LotInvoiceService;
import com.westflow.seeds_manager_api.application.service.LotService;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    private final LotMapper lotMapper;
    private final LotInvoiceService lotInvoiceService;
    private final com.westflow.seeds_manager_api.application.usecase.lot.DeleteLotUseCase deleteLotUseCase;

    @Override
    public LotResponse findById(Long id) {
        Lot lot = getLotById(id);

        List<LotInvoice> lotInvoices = lotInvoiceService.findAllByLotId(id);

        return lotMapper.toResponse(lot, lotInvoices);
    }

    @Override
    public Optional<Lot> findEntityById(Long id) {
        return lotRepository.findById(id);
    }

    @Override
    public void updateBalance(Lot lot, BigDecimal allocated) {
        //lot.withUpdatedBalance(allocated);
        lotRepository.save(lot);
    }

    @Override
    public void delete(Long id) {
        deleteLotUseCase.execute(id);
    }

    @Override
    public Page<LotResponse> findAll(Pageable pageable) {
        return lotRepository.findAll(pageable)
                .map(lot -> {
                    List<LotInvoice> lotInvoices = lotInvoiceService.findAllByLotId(lot.getId());
                    return lotMapper.toResponse(lot, lotInvoices);
                });
    }

    private Lot getLotById(Long id) {
        return findEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote", id));
    }
}
