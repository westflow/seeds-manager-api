package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.Client;

public interface ClientRepository {
    Client save(Client client);
}
