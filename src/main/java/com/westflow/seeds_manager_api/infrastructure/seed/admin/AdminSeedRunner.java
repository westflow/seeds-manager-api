package com.westflow.seeds_manager_api.infrastructure.seed.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "stage"})
@RequiredArgsConstructor
@Slf4j
public class AdminSeedRunner {

    private final AdminSeedService seedService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        log.info("[AdminSeedRunner] Executando seed após startup...");
        seedService.seedAdmin();
    }
}
