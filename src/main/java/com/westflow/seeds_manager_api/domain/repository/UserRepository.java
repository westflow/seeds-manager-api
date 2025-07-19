package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.User;

public interface UserRepository {
    User save(User user);
}
