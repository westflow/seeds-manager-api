package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.ClientService;
import com.westflow.seeds_manager_api.domain.entity.Client;
import com.westflow.seeds_manager_api.domain.repository.ClientRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaClientRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.ClientEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.ClientSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final JpaClientRepository jpaClientRepository;

    public ClientServiceImpl(ClientRepository clientRepository, JpaClientRepository jpaClientRepository) {
        this.clientRepository = clientRepository;
        this.jpaClientRepository = jpaClientRepository;
    }

    @Override
    public Client register(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public void delete(Long id) {
        ClientEntity entity = jpaClientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        if (!entity.isActive()) {
            throw new RuntimeException("Cliente já está inativo.");
        }
        entity.setActive(false);
        jpaClientRepository.save(entity);
    }

    public Page<ClientEntity> findAll(Pageable pageable) {
        Specification<ClientEntity> spec = ClientSpecifications.isActive();
        return jpaClientRepository.findAll(spec, pageable);
    }
}
