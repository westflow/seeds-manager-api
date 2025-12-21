package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.api.dto.request.LotWithdrawalRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
import com.westflow.seeds_manager_api.api.mapper.LotWithdrawalMapper;
import com.westflow.seeds_manager_api.application.usecase.client.FindClientByIdUseCase;
import com.westflow.seeds_manager_api.domain.model.Client;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotWithdrawal;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.LotWithdrawalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WithdrawLotUseCase {

    private final LotWithdrawalRepository lotWithdrawalRepository;
    private final LotWithdrawalMapper mapper;
    private final FindLotByIdUseCase findLotByIdUseCase;
    private final ConsumeLotBalanceUseCase consumeLotBalanceUseCase;
    private final FindClientByIdUseCase findClientByIdUseCase;

    @Transactional
    public LotWithdrawalResponse execute(LotWithdrawalRequest request, User user) {

        Lot lot = findLotByIdUseCase.execute(request.getLotId());

        Client client = findClientByIdUseCase.execute(request.getClientId());

        LotWithdrawal withdrawal = LotWithdrawal.newWithdrawal(
                lot,
                request.getInvoiceNumber(),
                request.getQuantity(),
                request.getWithdrawalDate(),
                request.getState(),
                user,
                client
        );

        LotWithdrawal saved =
                lotWithdrawalRepository.save(withdrawal);

        consumeLotBalanceUseCase.execute(lot, request.getQuantity());

        return mapper.toResponse(saved);
    }
}
