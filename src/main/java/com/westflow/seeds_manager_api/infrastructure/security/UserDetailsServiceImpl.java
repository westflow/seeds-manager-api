package com.westflow.seeds_manager_api.infrastructure.security;

import com.westflow.seeds_manager_api.domain.enums.SystemRole;
import com.westflow.seeds_manager_api.domain.model.Company;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.CompanyRepository;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    private final CompanyRepository companyRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if (Boolean.FALSE.equals(user.getActive())) {
            throw new DisabledException("Usuário inativo");
        }

        if (!SystemRole.SUPER_ADMIN.equals(user.getSystemRole())
                && user.getCompanyId() != null) {
            Company company = companyRepository.findById(user.getCompanyId())
                    .orElse(null);

            if (company == null || Boolean.FALSE.equals(company.getActive())) {
                throw new DisabledException("Empresa inativa");
            }
        }

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
