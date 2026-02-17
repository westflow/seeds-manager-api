package com.westflow.seeds_manager_api.infrastructure.security;

import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        user.updateLastLogin();
        userRepository.save(user);

        String tenantRole = user.getTenantRole() != null ? user.getTenantRole().name() : "STANDARD";
        String[] roles;
        if (user.getSystemRole() != null) {
            roles = new String[]{tenantRole, user.getSystemRole().name()};
        } else {
            roles = new String[]{tenantRole};
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail()) // aqui é o login
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}
