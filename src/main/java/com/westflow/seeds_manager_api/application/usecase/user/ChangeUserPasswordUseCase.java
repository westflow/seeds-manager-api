package com.westflow.seeds_manager_api.application.usecase.user;

import com.westflow.seeds_manager_api.api.dto.request.UserChangePasswordRequest;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeUserPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(User currentUser, UserChangePasswordRequest request) {
        if (!passwordEncoder.matches(request.getCurrentPassword(), currentUser.getPassword())) {
            throw new ValidationException("Senha atual inválida");
        }

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        User updated = currentUser.withEncodedPassword(encodedNewPassword);
        userRepository.save(updated);
    }
}
