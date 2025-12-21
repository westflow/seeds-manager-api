package com.westflow.seeds_manager_api.application.usecase.user;

import com.westflow.seeds_manager_api.domain.exception.UnauthorizedException;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindUserByEmailUseCase {

    private final UserRepository userRepository;

    public User execute(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UnauthorizedException::new);
    }
}
