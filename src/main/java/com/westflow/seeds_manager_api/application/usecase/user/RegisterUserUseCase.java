package com.westflow.seeds_manager_api.application.usecase.user;

import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User execute(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User securedUser = user.withEncodedPassword(encodedPassword);
        return userRepository.save(securedUser);
    }
}
