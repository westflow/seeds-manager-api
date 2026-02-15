package com.westflow.seeds_manager_api.application.usecase.user;

import com.westflow.seeds_manager_api.api.dto.request.UserAdminUpdateRequest;
import com.westflow.seeds_manager_api.api.dto.response.UserResponse;
import com.westflow.seeds_manager_api.api.mapper.UserMapper;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUpdateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse execute(Long userId, UserAdminUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", userId));

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new ValidationException("Email já está em uso");
            }
        }

        user.adminUpdate(
                request.getEmail(),
                request.getName(),
                request.getPosition(),
                request.getAccessLevel()
        );

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            String encodedNewPassword = passwordEncoder.encode(request.getPassword());
            user = user.withEncodedPassword(encodedNewPassword);
        }

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }
}
