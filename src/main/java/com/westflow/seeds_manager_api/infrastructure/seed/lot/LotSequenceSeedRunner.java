package com.westflow.seeds_manager_api.infrastructure.seed.lot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "stage", "prd"})
@RequiredArgsConstructor
@Slf4j
public class LotSequenceSeedRunner {

    private final LotSequenceSeedService seedService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        log.info("[LotSequenceSeedRunner] Executando seed de sequência de lote...");
        seedService.seedCurrentYearSequence();
    }
}
