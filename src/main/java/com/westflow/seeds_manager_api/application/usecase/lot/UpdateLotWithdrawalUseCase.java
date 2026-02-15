package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.api.dto.request.LotWithdrawalUpdateRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
import com.westflow.seeds_manager_api.api.mapper.LotWithdrawalMapper;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import com.westflow.seeds_manager_api.domain.model.LotWithdrawal;
import com.westflow.seeds_manager_api.domain.repository.LotWithdrawalRepository;
import com.westflow.seeds_manager_api.application.usecase.lot.ConsumeLotBalanceUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UpdateLotWithdrawalUseCase {

    private final LotWithdrawalRepository lotWithdrawalRepository;
    private final ConsumeLotBalanceUseCase consumeLotBalanceUseCase;
    private final LotWithdrawalMapper mapper;

    @Transactional
    public LotWithdrawalResponse execute(Long id, LotWithdrawalUpdateRequest request) {
        LotWithdrawal withdrawal = lotWithdrawalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Baixa de lote", id));

        if (Boolean.FALSE.equals(withdrawal.getActive())) {
            throw new ValidationException("Não é possível alterar uma baixa de lote removida");
        }

        BigDecimal oldQuantity = withdrawal.getQuantity();
        BigDecimal newQuantity = request.getQuantity();

        BigDecimal diff = newQuantity.subtract(oldQuantity);

        if (diff.signum() > 0) {
            consumeLotBalanceUseCase.consume(withdrawal.getLot(), diff);
        } else if (diff.signum() < 0) {
            consumeLotBalanceUseCase.refund(withdrawal.getLot(), diff.abs());
        }

        withdrawal.update(
                request.getInvoiceNumber(),
                request.getQuantity(),
                request.getWithdrawalDate(),
                request.getState()
        );

        LotWithdrawal saved = lotWithdrawalRepository.save(withdrawal);

        return mapper.toResponse(saved);
    }
}
