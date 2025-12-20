package com.westflow.seeds_manager_api.application.usecase.client;

import com.westflow.seeds_manager_api.domain.model.Client;
import com.westflow.seeds_manager_api.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPagedClientsUseCase {

    private final ClientRepository clientRepository;

    public Page<Client> execute(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }
}
