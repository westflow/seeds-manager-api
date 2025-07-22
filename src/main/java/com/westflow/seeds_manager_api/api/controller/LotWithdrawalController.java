package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.LotWithdrawalRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
import com.westflow.seeds_manager_api.api.mapper.LotWithdrawalMapper;
import com.westflow.seeds_manager_api.application.service.LotWithdrawalService;
import com.westflow.seeds_manager_api.domain.entity.LotWithdrawal;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/withdrawals")
public class LotWithdrawalController {

    private final LotWithdrawalService lotWithdrawalService;
    private final LotWithdrawalMapper lotWithdrawalMapper;

    public LotWithdrawalController(LotWithdrawalService lotWithdrawalService,
                                   LotWithdrawalMapper lotWithdrawalMapper) {
        this.lotWithdrawalService = lotWithdrawalService;
        this.lotWithdrawalMapper = lotWithdrawalMapper;
    }

    //@PreAuthorize("hasAnyRole('ADMIN', 'STANDARD')")
    @PostMapping
    public ResponseEntity<LotWithdrawalResponse> withdraw(@Valid @RequestBody LotWithdrawalRequest request) {

        LotWithdrawal withdrawal = lotWithdrawalMapper.toDomain(request);
        LotWithdrawal saved = lotWithdrawalService.withdraw(withdrawal);
        LotWithdrawalResponse response = lotWithdrawalMapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
