package com.westflow.seeds_manager_api.application.usecase.client;

import com.westflow.seeds_manager_api.domain.model.Client;
import com.westflow.seeds_manager_api.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteClientUseCase {

    private final ClientRepository clientRepository;
    private final FindClientByIdUseCase findClientByIdUseCase;

    public void execute(Long id) {
        Client client = findClientByIdUseCase.execute(id);
        client.deactivate();
        clientRepository.save(client);
    }
}
