package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    boolean existsByEmail(String email);
}
