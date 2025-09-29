package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.ClientRequest;
import com.westflow.seeds_manager_api.api.dto.response.ClientResponse;
import com.westflow.seeds_manager_api.domain.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClientService {
    ClientResponse register(ClientRequest request);
    ClientResponse findById(Long id);
    Optional<Client> findEntityById(Long id);
    Page<ClientResponse> findAll(Pageable pageable);
    ClientResponse update(Long id, ClientRequest request);
    void delete(Long id);
}
