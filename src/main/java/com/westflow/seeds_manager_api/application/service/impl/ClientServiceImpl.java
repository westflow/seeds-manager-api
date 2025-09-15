package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.ClientService;
import com.westflow.seeds_manager_api.domain.entity.Client;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client register(Client client) {
        clientRepository.findByNumber(client.getNumber())
            .ifPresent(existing -> {
                throw new BusinessException("Já existe um cliente cadastrado com esse número.");
            });
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Client update(Long id, Client client) {
        Client existing = findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", id));

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

        return clientRepository.save(updated);
    }

    @Override
    public void delete(Long id) {
        Client client = findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente", id));

        client.deactivate();
        clientRepository.save(client);
    }
}
