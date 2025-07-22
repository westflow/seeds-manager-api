package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.security.JwtTokenProvider;
import com.westflow.seeds_manager_api.application.service.AuthService;
import com.westflow.seeds_manager_api.domain.exception.DomainException;
import com.westflow.seeds_manager_api.infrastructure.security.UserDetailsServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserDetailsServiceImpl userDetailsService,
                           JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(String email, String rawPassword) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            throw new DomainException("Credenciais inválidas");
        }

        return jwtTokenProvider.generateToken(userDetails);
    }
}
