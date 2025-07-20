package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.entity.Client;
import com.westflow.seeds_manager_api.domain.repository.ClientRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.ClientEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.ClientPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaClientRepository;
import org.springframework.stereotype.Component;

@Component
public class ClientRepositoryAdapter implements ClientRepository {
    private final JpaClientRepository jpaRepository;
    private final ClientPersistenceMapper mapper;

    public ClientRepositoryAdapter(JpaClientRepository jpaRepository, ClientPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Client save(Client client) {
        ClientEntity entity = mapper.toEntity(client);
        return mapper.toDomain(jpaRepository.save(entity));
    }
}
