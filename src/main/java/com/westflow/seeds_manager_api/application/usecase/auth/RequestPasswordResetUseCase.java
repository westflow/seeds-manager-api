package com.westflow.seeds_manager_api.application.usecase.auth;

import com.westflow.seeds_manager_api.api.security.JwtTokenProvider;
import com.westflow.seeds_manager_api.domain.event.PasswordResetRequestedEvent;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestPasswordResetUseCase {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ApplicationEventPublisher eventPublisher;

    public void execute(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", email));

        String token = jwtTokenProvider.generateResetToken(user.getEmail());

        eventPublisher.publishEvent(new PasswordResetRequestedEvent(user.getEmail(), token));
    }
}
