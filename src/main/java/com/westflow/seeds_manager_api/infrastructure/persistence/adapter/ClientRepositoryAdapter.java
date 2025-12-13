package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.model.Client;
import com.westflow.seeds_manager_api.domain.repository.ClientRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.ClientEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.ClientPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaClientRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.ClientSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    @Override
    public Optional<Client> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Client> findByNumber(String number) {
        return jpaRepository.findByNumber(number).map(mapper::toDomain);
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        Specification<ClientEntity> spec = ClientSpecifications.isActive();
        return jpaRepository.findAll(spec, pageable).map(mapper::toDomain);
    }
}
