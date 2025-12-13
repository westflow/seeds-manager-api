package com.westflow.seeds_manager_api.infrastructure.seed.lot;

import com.westflow.seeds_manager_api.domain.model.LotSequence;
import com.westflow.seeds_manager_api.domain.repository.LotSequenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class LotSequenceSeedService {

    @Value("${app.seed.lot-sequence.enabled:false}")
    private boolean isSeedEnabled;

    private final LotSequenceRepository repository;

    public void seedCurrentYearSequence() {
        if (!isSeedEnabled) {
            log.warn("[LotSequenceSeedService] Seed de sequência de lote desativado por configuração.");
            return;
        }

        if (repository.existsByResetDoneFalse()) {
            log.info("[LotSequenceSeedService] Já existe uma sequência de lote pendente. Nenhuma nova será criada.");
            return;
        }

        int currentYear = LocalDate.now().getYear();

        try {
            LotSequence sequence = LotSequence.builder()
                    .year(currentYear)
                    .lastNumber(0)
                    .resetDone(false)
                    .resetDate(null)
                    .createdAt(LocalDateTime.now())
                    .build();

            repository.save(sequence);
            log.info("[LotSequenceSeedService] Sequência de lote criada para o ano {}.", currentYear);
        } catch (Exception ex) {
            log.error("[LotSequenceSeedService] Erro ao criar sequência de lote. Tipo: {}", ex.getClass().getSimpleName(), ex);
        }
    }
}
