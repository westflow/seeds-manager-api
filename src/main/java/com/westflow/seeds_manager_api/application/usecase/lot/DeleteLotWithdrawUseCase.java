package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.LotWithdrawal;
import com.westflow.seeds_manager_api.domain.repository.LotWithdrawalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteLotWithdrawUseCase {

    private final LotWithdrawalRepository lotWithdrawalRepository;
    private final ConsumeLotBalanceUseCase consumeLotBalanceUseCase;

    @Transactional
    public void execute(Long id) {

        LotWithdrawal lotWithdrawal = lotWithdrawalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Baixa de lote", id));

        lotWithdrawal.deactivate();

        consumeLotBalanceUseCase.refund(lotWithdrawal.getLot(), lotWithdrawal.getQuantity());

        lotWithdrawalRepository.save(lotWithdrawal);
    }
}
