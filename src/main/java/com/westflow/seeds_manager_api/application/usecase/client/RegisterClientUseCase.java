package com.westflow.seeds_manager_api.application.usecase.client;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.model.Client;
import com.westflow.seeds_manager_api.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterClientUseCase {

    private final ClientRepository clientRepository;

    public Client execute(Client client) {
        clientRepository.findByNumber(client.getNumber())
                .ifPresent(existing -> {
                    throw new BusinessException("Já existe um cliente cadastrado com esse número.");
                });

        return clientRepository.save(client);
    }
}
