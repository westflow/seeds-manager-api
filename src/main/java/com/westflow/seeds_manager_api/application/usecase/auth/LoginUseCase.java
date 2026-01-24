package com.westflow.seeds_manager_api.application.usecase.auth;

import com.westflow.seeds_manager_api.api.security.JwtTokenProvider;
import com.westflow.seeds_manager_api.domain.exception.DomainException;
import com.westflow.seeds_manager_api.infrastructure.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public String execute(String email, String rawPassword) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            throw new DomainException("Credenciais inválidas");
        }

        return jwtTokenProvider.generateToken(userDetails);
    }
}
