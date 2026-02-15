package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ConsumeLotBalanceUseCase {

    private final LotRepository lotRepository;

    public void execute(Lot lot, BigDecimal quantity) {
        consume(lot, quantity);
    }

    public void consume(Lot lot, BigDecimal quantity) {
        lot.decreaseBalance(quantity);
        lotRepository.save(lot);
    }

    public void refund(Lot lot, BigDecimal quantity) {
        lot.increaseBalance(quantity);
        lotRepository.save(lot);
    }
}
