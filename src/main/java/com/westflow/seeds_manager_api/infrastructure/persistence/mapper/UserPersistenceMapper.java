package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {
    UserEntity toEntity(User domain);

    default User toDomain(UserEntity entity) {
        if (entity == null) return null;

        return User.restore(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getPosition(),
                entity.getAccessLevel(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getLastLogin(),
                entity.getActive()
        );
    }
}
