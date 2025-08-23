package com.westflow.seeds_manager_api.api.controller;


import com.westflow.seeds_manager_api.api.dto.request.BagWeightCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.BagWeightResponse;
import com.westflow.seeds_manager_api.api.mapper.BagWeightMapper;
import com.westflow.seeds_manager_api.application.service.BagWeightService;
import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/api/bag-weights")
@Tag(name = "BagWeights", description = "Operações de peso de sacaria")
public class BagWeightController {

    private final BagWeightService service;
    private final BagWeightMapper mapper;

    public BagWeightController(BagWeightService service, BagWeightMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Cria novo peso de sacaria", responses = {
            @ApiResponse(responseCode = "201", description = "Peso criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BagWeightResponse> register(@Valid @RequestBody BagWeightCreateRequest request) {
        BagWeight saved = service.register(mapper.toDomain(request));
        BagWeightResponse response = mapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
