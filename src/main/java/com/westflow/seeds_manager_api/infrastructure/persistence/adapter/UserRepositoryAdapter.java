package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.entity.User;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.UserEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {
    private final JpaUserRepository jpaRepository;
    private final UserPersistenceMapper mapper;

    public UserRepositoryAdapter(JpaUserRepository jpaRepository, UserPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
