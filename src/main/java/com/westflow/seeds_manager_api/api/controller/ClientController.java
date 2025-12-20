package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.ClientRequest;
import com.westflow.seeds_manager_api.api.dto.response.ClientResponse;
import com.westflow.seeds_manager_api.api.mapper.ClientMapper;
import com.westflow.seeds_manager_api.application.usecase.client.DeleteClientUseCase;
import com.westflow.seeds_manager_api.application.usecase.client.FindPagedClientsUseCase;
import com.westflow.seeds_manager_api.application.usecase.client.FindClientByIdUseCase;
import com.westflow.seeds_manager_api.application.usecase.client.RegisterClientUseCase;
import com.westflow.seeds_manager_api.application.usecase.client.UpdateClientUseCase;
import com.westflow.seeds_manager_api.domain.model.Client;
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
@RequestMapping("/api/clients")
@Tag(name = "Clients", description = "Operações de clientes")
public class ClientController {

    private final ClientMapper clientMapper;
    private final RegisterClientUseCase registerClientUseCase;
    private final FindPagedClientsUseCase findPagedClientsUseCase;
    private final FindClientByIdUseCase findClientByIdUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;

    @Operation(
            summary = "Cria um novo cliente",
            description = "Valida e registra um novo cliente no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody ClientRequest request) {
        Client client = Client.newClient(
                request.getNumber(),
                request.getName(),
                request.getEmail(),
                request.getPhone()
        );
        Client saved = registerClientUseCase.execute(client);
        ClientResponse response = clientMapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Lista todos os clientes",
            description = "Retorna uma lista paginada de clientes ativos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping
    public ResponseEntity<Page<ClientResponse>> listAll(@ParameterObject Pageable pageable) {
        Page<Client> clients = findPagedClientsUseCase.execute(pageable);
        Page<ClientResponse> response = clients.map(clientMapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Busca cliente por ID",
            description = "Retorna um cliente ativo pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable Long id) {
        Client client = findClientByIdUseCase.execute(id);
        ClientResponse response = clientMapper.toResponse(client);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Atualiza um cliente",
            description = "Atualiza os dados de um cliente existente (exceto o número)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente atualizado"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id, @Valid @RequestBody ClientRequest request) {
        Client client = Client.newClient(
                request.getNumber(),
                request.getName(),
                request.getEmail(),
                request.getPhone()
        );
        Client saved = updateClientUseCase.execute(id, client);
        ClientResponse response = clientMapper.toResponse(saved);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Remove um cliente",
            description = "Remove logicamente um cliente pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente removido"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteClientUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
