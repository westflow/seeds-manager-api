package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.LotWithdrawalRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
import com.westflow.seeds_manager_api.api.mapper.LotWithdrawalMapper;
import com.westflow.seeds_manager_api.application.service.ClientService;
import com.westflow.seeds_manager_api.application.service.LotService;
import com.westflow.seeds_manager_api.application.service.LotWithdrawalService;
import com.westflow.seeds_manager_api.domain.entity.Client;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.entity.LotWithdrawal;
import com.westflow.seeds_manager_api.domain.entity.User;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.LotWithdrawalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LotWithdrawalServiceImpl implements LotWithdrawalService {

    private final LotWithdrawalRepository lotWithdrawalRepository;
    private final LotWithdrawalMapper mapper;
    private final LotService lotService;
    private final ClientService clientService;

    @Override
    @Transactional
    public LotWithdrawalResponse withdraw(LotWithdrawalRequest request, User user) {
        Lot lot = lotService.findEntityById(request.getLotId())
                .orElseThrow(() -> new ResourceNotFoundException("Lote", request.getLotId()));

        Client client = clientService.findEntityById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", request.getClientId()));

        LotWithdrawal lotWithdrawal = mapper.toDomain(request, user, lot, client);
        LotWithdrawal savedWithdrawal = lotWithdrawalRepository.save(lotWithdrawal);
        lotService.updateBalance(lot, request.getQuantity());
        return mapper.toResponse(savedWithdrawal);
    }
}
