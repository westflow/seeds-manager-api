package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.Client;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientPersistenceMapper {

    ClientEntity toEntity(Client domain);

    default Client toDomain(ClientEntity entity) {
        if (entity == null) {
            return null;
        }

        return Client.restore(
                entity.getId(),
                entity.getNumber(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
