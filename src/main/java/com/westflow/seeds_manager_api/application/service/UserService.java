package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.UserCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.UserResponse;
import com.westflow.seeds_manager_api.domain.entity.User;

import java.util.Optional;

public interface UserService {
    UserResponse register(UserCreateRequest request);
    Optional<User> getByEmail(String email);
}
