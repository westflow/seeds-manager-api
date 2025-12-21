package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.UserCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.UserResponse;
import com.westflow.seeds_manager_api.api.mapper.UserMapper;
import com.westflow.seeds_manager_api.application.service.UserService;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.UserEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaUserRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.UserSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse register(UserCreateRequest request) {
        User user = userMapper.toDomain(request);
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
                user.getLastLogin(),
                user.getActive()
        );
        return userMapper.toResponse(userRepository.save(securedUser));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void delete(Long id) {
        UserEntity entity = jpaUserRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
        if (!entity.getActive()) {
            throw new ValidationException("Usuário já está inativo.");
        }
        entity.setActive(false);
        jpaUserRepository.save(entity);
    }

    public Page<UserEntity> findAll(Pageable pageable) {
        Specification<UserEntity> spec = UserSpecifications.isActive();
        return jpaUserRepository.findAll(spec, pageable);
    }
}
