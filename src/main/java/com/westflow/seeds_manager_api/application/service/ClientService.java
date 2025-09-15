package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClientService {
    Client register(Client client);
    Optional<Client> findById(Long id);
    Page<Client> findAll(Pageable pageable);
    Client update(Long id, Client client);
    void delete(Long id);
}
