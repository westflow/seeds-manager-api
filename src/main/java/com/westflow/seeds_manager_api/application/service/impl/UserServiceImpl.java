package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.UserService;
import com.westflow.seeds_manager_api.domain.entity.User;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User securedUser = new User(
                user.getId(),
                user.getEmail(),
                encodedPassword,
                user.getName(),
                user.getPosition(),
                user.getAccessLevel(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getLastLogin()
        );
        return userRepository.save(securedUser);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
