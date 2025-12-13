package com.westflow.seeds_manager_api.infrastructure.seed.admin;

import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminSeedService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.seed.email}")
    private String seedEmail;

    @Value("${admin.seed.password}")
    private String seedPassword;

    @Value("${app.seed.admin.enabled:false}")
    private boolean isSeedEnabled;

    public void seedAdmin() {
        if (!isSeedEnabled) {
            log.warn("[AdminSeedService] Seed ADMIN desativado por configuração.");
            return;
        }

        if (seedEmail == null || seedEmail.isBlank() || seedPassword == null || seedPassword.isBlank()) {
            log.error("[AdminSeedService] Propriedades 'admin.seed.email' ou 'admin.seed.password' não definidas ou vazias.");
            return;
        }

        if (repository.existsByEmail(seedEmail)) {
            log.info("[AdminSeedService] Seed ADMIN já existe: {}", seedEmail);
            return;
        }

        try {
            User admin = new User(
                    null,
                    seedEmail,
                    passwordEncoder.encode(seedPassword),
                    "Administrador",
                    "Admin",
                    AccessLevel.ADMIN,
                    LocalDateTime.now(),
                    null,
                    null,
                    true
            );

            repository.save(admin);
            log.info("[AdminSeedService] Seed ADMIN criado com sucesso: {}", seedEmail);
        } catch (Exception ex) {
            log.error("[AdminSeedService] Erro ao criar seed ADMIN. Tipo: {}", ex.getClass().getSimpleName(), ex);
        }
    }
}
