package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.config.CurrentUser;
import com.westflow.seeds_manager_api.api.dto.request.LotCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.api.mapper.LotMapper;
import com.westflow.seeds_manager_api.application.service.LotService;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Lots", description = "Operações de lotes")
@RestController
@RequestMapping("/api/lots")
public class LotController {

    private final LotService lotService;
    private final LotMapper lotMapper;

    public LotController(LotService lotService, LotMapper lotMapper) {
        this.lotService = lotService;
        this.lotMapper = lotMapper;
    }

    @Operation(
            summary = "Cria um novo lote",
            description = "Valida e registra um novo lote vinculado ao usuário logado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Lote criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LotResponse> create(@Valid @RequestBody LotCreateRequest request,
                                              @Parameter(hidden = true) @CurrentUser User user) {
        Lot saved = lotService.register(request, user);
        LotResponse response = lotMapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
