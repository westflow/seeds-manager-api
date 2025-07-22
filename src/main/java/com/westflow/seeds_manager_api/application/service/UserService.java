package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.User;

import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> getByEmail(String email);
}
