package com.westflow.seeds_manager_api.infrastructure.seed.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "stage"})
@RequiredArgsConstructor
@Slf4j
public class FlywayAdminSeedCallback implements Callback {
    private final AdminSeedService seedService;

    @Override
    public boolean supports(Event event, Context context) {
        return event == Event.AFTER_MIGRATE;
    }

    @Override
    public void handle(Event event, Context context) {
        log.info("[FlywayAdminSeedCallback] Executando seed após migração...");
        seedService.seedAdmin();
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return false;
    }

    @Override
    public String getCallbackName() {
        return "flyway-admin-seed-callback";
    }
}
