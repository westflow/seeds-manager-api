package com.westflow.seeds_manager_api.application.usecase.auth;

import com.westflow.seeds_manager_api.api.security.JwtTokenProvider;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResetPasswordUseCase {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(String token, String newPassword) {
        String email;
        try {
            email = jwtTokenProvider.getResetEmailFromToken(token);
        } catch (JwtException | IllegalArgumentException ex) {
            throw new ValidationException("Token de reset inválido ou expirado");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", email));

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        User updated = user.withEncodedPassword(encodedNewPassword);
        userRepository.save(updated);
    }
}
