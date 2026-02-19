package com.westflow.seeds_manager_api.application.usecase.auth;

import com.westflow.seeds_manager_api.api.security.JwtTokenProvider;
import com.westflow.seeds_manager_api.domain.exception.DomainException;
import com.westflow.seeds_manager_api.domain.model.Company;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.CompanyRepository;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public String execute(String email, String rawPassword) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            throw new DomainException("Credenciais inválidas");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException("Usuário não encontrado"));

        Long tenantId = user.getCompanyId();
        String tenantCode = null;

        if (tenantId != null) {
            Company company = companyRepository.findById(tenantId)
                    .orElseThrow(() -> new DomainException("Empresa do usuário não encontrada"));
            tenantCode = company.getTenantCode();
        }

        return jwtTokenProvider.generateToken(userDetails, tenantId, tenantCode);
    }
}
