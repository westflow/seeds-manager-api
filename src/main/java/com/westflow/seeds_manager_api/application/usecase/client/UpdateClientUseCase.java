package com.westflow.seeds_manager_api.application.usecase.client;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.model.Client;
import com.westflow.seeds_manager_api.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateClientUseCase {

    private final ClientRepository clientRepository;
    private final FindClientByIdUseCase findClientByIdUseCase;

    public Client execute(Long id, Client clientData) {
        Client existing = findClientByIdUseCase.execute(id);

        if (!existing.getNumber().equals(clientData.getNumber())) {
            clientRepository.findByNumber(clientData.getNumber())
                .ifPresent(duplicate -> {
                    throw new BusinessException("Já existe um cliente cadastrado com esse número.");
                });
        }

        existing.update(
                clientData.getNumber(),
                clientData.getName(),
                clientData.getEmail(),
                clientData.getPhone()
        );

        return clientRepository.save(existing);
    }
}
