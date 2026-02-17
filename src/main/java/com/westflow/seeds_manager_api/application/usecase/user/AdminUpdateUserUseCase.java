package com.westflow.seeds_manager_api.application.usecase.user;

import com.westflow.seeds_manager_api.api.dto.request.UserAdminUpdateRequest;
import com.westflow.seeds_manager_api.api.dto.response.UserResponse;
import com.westflow.seeds_manager_api.api.mapper.UserMapper;
import com.westflow.seeds_manager_api.domain.enums.SystemRole;
import com.westflow.seeds_manager_api.domain.enums.TenantRole;
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
    public UserResponse execute(User currentUser, Long userId, UserAdminUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", userId));

        validateUser(currentUser, user, request);

        user.adminUpdate(
                request.getEmail(),
                request.getName(),
                request.getPosition(),
                request.getTenantRole()
        );

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            String encodedNewPassword = passwordEncoder.encode(request.getPassword());
            user = user.withEncodedPassword(encodedNewPassword);
        }

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    private void validateUser(User currentUser, User user, UserAdminUpdateRequest request) {
        validateOwnerRules(currentUser, user);
        validateAdminRules(currentUser, user, request);
        validateStandardRules(currentUser, user);
        validateEmailChange(user, request);
    }

    private boolean isSuperAdmin(User currentUser) {
        return currentUser.getSystemRole() != SystemRole.SUPER_ADMIN;
    }

    private void validateOwnerRules(User currentUser, User user) {
        if (user.getTenantRole() == TenantRole.OWNER && isSuperAdmin(currentUser)) {
            throw new ValidationException("Apenas SUPER_ADMIN pode alterar um usuário OWNER");
        }
    }

    private void validateAdminRules(User currentUser, User user, UserAdminUpdateRequest request) {
        if (user.getTenantRole() == TenantRole.ADMIN && isSuperAdmin(currentUser)) {
            if (currentUser.getTenantRole() != TenantRole.OWNER) {
                throw new ValidationException("Apenas OWNER ou SUPER_ADMIN podem alterar um usuário ADMIN");
            }

            if (currentUser.getId().equals(user.getId())
                    && request.getTenantRole() != null
                    && request.getTenantRole() != user.getTenantRole()) {
                throw new ValidationException("ADMIN não pode alterar seu próprio perfil de acesso");
            }
        }
    }

    private void validateStandardRules(User currentUser, User user) {
        if (user.getTenantRole() == TenantRole.STANDARD || user.getTenantRole() == TenantRole.READ_ONLY) {
            if (isSuperAdmin(currentUser)
                    && currentUser.getTenantRole() != TenantRole.ADMIN
                    && currentUser.getTenantRole() != TenantRole.OWNER) {
                throw new ValidationException("Apenas ADMIN, OWNER ou SUPER_ADMIN podem alterar este usuário");
            }
        }
    }

    private void validateEmailChange(User user, UserAdminUpdateRequest request) {
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new ValidationException("Email já está em uso");
            }
        }
    }
}
