package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.entity.Client;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientPersistenceMapper {
    ClientEntity toEntity(Client domain);
    Client toDomain(ClientEntity entity);
}
