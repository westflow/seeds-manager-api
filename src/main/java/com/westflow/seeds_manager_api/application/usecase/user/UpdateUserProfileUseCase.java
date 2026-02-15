package com.westflow.seeds_manager_api.application.usecase.user;

import com.westflow.seeds_manager_api.api.dto.request.UserProfileUpdateRequest;
import com.westflow.seeds_manager_api.api.dto.response.UserResponse;
import com.westflow.seeds_manager_api.api.mapper.UserMapper;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserProfileUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse execute(User currentUser, UserProfileUpdateRequest request) {
        currentUser.updateProfile(request.getName(), request.getPosition());
        User saved = userRepository.save(currentUser);
        return userMapper.toResponse(saved);
    }
}
