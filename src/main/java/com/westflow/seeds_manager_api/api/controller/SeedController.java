package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.SeedCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.SeedResponse;
import com.westflow.seeds_manager_api.api.mapper.SeedMapper;
import com.westflow.seeds_manager_api.application.service.SeedService;
import com.westflow.seeds_manager_api.domain.entity.Seed;
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

@Tag(name = "Seeds", description = "Operações de sementes")
@RestController
@RequestMapping("/api/seeds")
public class SeedController {

    private final SeedService seedService;
    private final SeedMapper seedMapper;

    public SeedController(SeedService seedService, SeedMapper seedMapper) {
        this.seedService = seedService;
        this.seedMapper = seedMapper;
    }

    @Operation(
            summary = "Cria uma nova semente",
            description = "Valida e persiste uma semente no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Semente criada"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SeedResponse> register(@Valid @RequestBody SeedCreateRequest request) {

        Seed seed = seedMapper.toDomain(request);
        Seed saved = seedService.register(seed);
        SeedResponse response = seedMapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
