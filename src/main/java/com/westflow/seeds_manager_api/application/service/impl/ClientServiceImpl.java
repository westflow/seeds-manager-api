package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.ClientRequest;
import com.westflow.seeds_manager_api.api.dto.response.ClientResponse;
import com.westflow.seeds_manager_api.api.mapper.ClientMapper;
import com.westflow.seeds_manager_api.application.service.ClientService;
import com.westflow.seeds_manager_api.domain.entity.Client;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper mapper;

    @Override
    public ClientResponse register(ClientRequest request) {
        Client client = mapper.toDomain(request);
        
        clientRepository.findByNumber(client.getNumber())
            .ifPresent(existing -> {
                throw new BusinessException("Já existe um cliente cadastrado com esse número.");
            });
            
        Client saved = clientRepository.save(client);
        return mapper.toResponse(saved);
    }

    @Override
    public ClientResponse findById(Long id) {
        return mapper.toResponse(getClientById(id));
    }

    @Override
    public Page<ClientResponse> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public ClientResponse update(Long id, ClientRequest request) {
        Client client = mapper.toDomain(request);
        Client existing = getClientById(id);

        if (!existing.isActive()) {
            throw new BusinessException("Cliente está inativo e não pode ser atualizado.");
        }

        if (!existing.getNumber().equals(client.getNumber())) {
            clientRepository.findByNumber(client.getNumber())
                .ifPresent(duplicate -> {
                    throw new BusinessException("Já existe um cliente cadastrado com esse número.");
                });
        }

        Client updated = Client.builder()
            .id(existing.getId())
            .name(client.getName())
            .number(client.getNumber())
            .phone(client.getPhone())
            .email(client.getEmail())
            .build();

        Client saved = clientRepository.save(updated);
        return mapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        Client client = getClientById(id);
        client.deactivate();
        clientRepository.save(client);
    }
    
    @Override
    public Optional<Client> findEntityById(Long id) {
        return clientRepository.findById(id);
    }
    
    private Client getClientById(Long id) {
        return findEntityById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", id));
    }
}
