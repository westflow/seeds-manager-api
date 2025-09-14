package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.UserService;
import com.westflow.seeds_manager_api.domain.entity.User;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaUserRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.UserEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.UserSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JpaUserRepository jpaUserRepository;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JpaUserRepository jpaUserRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jpaUserRepository = jpaUserRepository;
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

    public void delete(Long id) {
        UserEntity entity = jpaUserRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        if (!entity.isActive()) {
            throw new RuntimeException("Usuário já está inativo.");
        }
        entity.setActive(false);
        jpaUserRepository.save(entity);
    }

    public Page<UserEntity> findAll(Pageable pageable) {
        Specification<UserEntity> spec = UserSpecifications.isActive();
        return jpaUserRepository.findAll(spec, pageable);
    }
}
