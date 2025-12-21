package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.SeedRequest;
import com.westflow.seeds_manager_api.api.dto.response.SeedResponse;
import com.westflow.seeds_manager_api.api.mapper.SeedMapper;
import com.westflow.seeds_manager_api.application.usecase.seed.*;
import com.westflow.seeds_manager_api.domain.model.Seed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seeds")
@Tag(name = "Seeds", description = "Operações de sementes")
public class SeedController {

    private final SeedMapper mapper;
    private final RegisterSeedUseCase registerSeedUseCase;
    private final FindPagedSeedsUseCase findPagedSeedsUseCase;
    private final FindSeedByIdUseCase findSeedByIdUseCase;
    private final UpdateSeedUseCase updateSeedUseCase;
    private final DeleteSeedUseCase deleteSeedUseCase;

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
    public ResponseEntity<SeedResponse> register(@Valid @RequestBody SeedRequest request) {
        Seed seed = Seed.newSeed(
                request.getSpecies(),
                request.getCultivar(),
                request.isProtected()
        );

        Seed saved = registerSeedUseCase.execute(seed);
        SeedResponse response = mapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Lista todas as sementes",
            description = """
                    Retorna uma lista paginada de sementes.
                    
                    Parâmetros de paginação e ordenação:
                    - page: número da página (começa em 0)
                    - size: quantidade de itens por página
                    - sort: critérios de ordenação, no formato 'campo,direção' (ex: 'id,asc'). Pode ser informado múltiplas vezes.
                    - protected: filtra sementes protegidas (true) ou não protegidas (false)
                    
                    Exemplo: ?page=0&size=10&sort=id,asc&protected=true
                    """
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping
    public ResponseEntity<Page<SeedResponse>> listAll(
            @ParameterObject Pageable pageable,
            @RequestParam(value = "protected", required = false) Boolean isProtected
    ) {
        Page<Seed> page = findPagedSeedsUseCase.execute(isProtected, pageable);
        Page<SeedResponse> response = page.map(mapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Busca uma semente por ID",
            description = "Retorna uma semente pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Semente encontrada"),
                    @ApiResponse(responseCode = "404", description = "Semente não encontrada")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping("/{id}")
    public ResponseEntity<SeedResponse> getById(@PathVariable Long id) {
        Seed seed = findSeedByIdUseCase.execute(id);
        SeedResponse response = mapper.toResponse(seed);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Atualiza uma semente",
            description = "Atualiza os dados de uma semente existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Semente atualizada"),
                    @ApiResponse(responseCode = "404", description = "Semente não encontrada"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SeedResponse> update(@PathVariable Long id, @Valid @RequestBody SeedRequest request) {
        Seed seed = updateSeedUseCase.execute(id, request);
        SeedResponse response = mapper.toResponse(seed);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Remove uma semente",
            description = "Remove uma semente pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Semente removida"),
                    @ApiResponse(responseCode = "404", description = "Semente não encontrada")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteSeedUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
