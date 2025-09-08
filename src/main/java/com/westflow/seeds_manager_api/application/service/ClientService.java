package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.Client;

import java.util.Optional;

public interface ClientService {
    Client register(Client client);
    Optional<Client> findById(Long id);
}
