package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.SeedCreateRequest;
import com.westflow.seeds_manager_api.api.dto.request.SeedUpdateRequest;
import com.westflow.seeds_manager_api.api.dto.response.SeedResponse;
import com.westflow.seeds_manager_api.api.mapper.SeedMapper;
import com.westflow.seeds_manager_api.application.service.SeedService;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        Page<SeedResponse> page = seedService.findAll(isProtected, pageable)
                .map(seedMapper::toResponse);
        return ResponseEntity.ok(page);
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
        return seedService.findById(id)
                .map(seedMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    public ResponseEntity<SeedResponse> update(@PathVariable Long id, @Valid @RequestBody SeedUpdateRequest request) {
        try {
            Seed updated = seedService.update(id, seedMapper.toDomain(request, id));
            return ResponseEntity.ok(seedMapper.toResponse(updated));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
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
        try {
            seedService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
