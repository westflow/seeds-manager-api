package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import com.westflow.seeds_manager_api.domain.repository.LotInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindLotInvoicesByLotIdUseCase {

    private final LotInvoiceRepository lotInvoiceRepository;

    public List<LotInvoice> execute(Long lotId) {
        return lotInvoiceRepository.findAllByLotId(lotId);
    }
}
