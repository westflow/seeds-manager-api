package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.LotWithdrawalRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
import com.westflow.seeds_manager_api.api.mapper.LotWithdrawalMapper;
import com.westflow.seeds_manager_api.application.service.ClientService;
import com.westflow.seeds_manager_api.application.service.LotWithdrawalService;
import com.westflow.seeds_manager_api.application.usecase.lot.ConsumeLotBalanceUseCase;
import com.westflow.seeds_manager_api.application.usecase.lot.FindLotByIdUseCase;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Client;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotWithdrawal;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.LotWithdrawalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LotWithdrawalServiceImpl implements LotWithdrawalService {

    private final LotWithdrawalRepository lotWithdrawalRepository;
    private final LotWithdrawalMapper mapper;
    private final ClientService clientService;
    private final FindLotByIdUseCase findLotByIdUseCase;
    private final ConsumeLotBalanceUseCase consumeLotBalanceUseCase;

    @Override
    @Transactional
    public LotWithdrawalResponse withdraw(LotWithdrawalRequest request, User user) {
        Lot lot = findLotByIdUseCase.execute(request.getLotId());

        Client client = clientService.findEntityById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", request.getClientId()));

        LotWithdrawal lotWithdrawal = mapper.toDomain(request, user, lot, client);
        LotWithdrawal savedWithdrawal = lotWithdrawalRepository.save(lotWithdrawal);
        consumeLotBalanceUseCase.execute(lot, request.getQuantity());
        return mapper.toResponse(savedWithdrawal);
    }
}
