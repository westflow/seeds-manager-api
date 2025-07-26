package com.westflow.seeds_manager_api.infrastructure.seed.admin;

import com.westflow.seeds_manager_api.domain.entity.User;
import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

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
                    LocalDateTime.now(),
                    null
            );

            repository.save(admin);
            log.info("[AdminSeedService] Seed ADMIN criado com sucesso: {}", seedEmail);
        } catch (Exception ex) {
            log.error("[AdminSeedService] Erro ao criar seed ADMIN. Tipo: {}", ex.getClass().getSimpleName(), ex);
        }
    }
}
