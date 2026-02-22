package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.model.LotSequence;
import com.westflow.seeds_manager_api.domain.repository.LotSequenceRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotSequenceEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.LotSequencePersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaLotSequenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class LotSequenceRepositoryAdapter implements LotSequenceRepository {

    private final JpaLotSequenceRepository jpaRepository;
    private final LotSequencePersistenceMapper mapper;

    public LotSequenceRepositoryAdapter(JpaLotSequenceRepository jpaRepository,
                                        LotSequencePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public String generateFormattedLotNumber(Long companyId) {
        Optional<LotSequenceEntity> optionalEntity = jpaRepository.findTopByCompanyIdAndResetDoneFalseOrderByYearDesc(companyId);

        LotSequenceEntity entity = optionalEntity.orElseGet(() -> {
            int currentYear = LocalDate.now().getYear();
            LotSequenceEntity newEntity = new LotSequenceEntity();
            newEntity.setYear(currentYear);
            newEntity.setLastNumber(0);
            newEntity.setResetDone(false);
            newEntity.setCreatedAt(LocalDateTime.now());
            newEntity.setCompanyId(companyId);
            return newEntity;
        });

        entity.setLastNumber(entity.getLastNumber() + 1);
        LotSequenceEntity saved = jpaRepository.save(entity);

        return String.format("%04d/%d", saved.getLastNumber(), saved.getYear());
    }

    @Override
    @Transactional
    public LotSequence resetPreviousAndCreateCurrent(Long companyId) {
        int currentYear = LocalDate.now().getYear();

        LotSequenceEntity previous = jpaRepository.findTopByCompanyIdAndResetDoneFalseOrderByYearDesc(companyId)
                .orElseThrow(() -> new IllegalStateException("Nenhuma sequência anterior encontrada para reset."));

        if (previous.getYear() == currentYear) {
            throw new IllegalStateException("Não é permitido resetar o ano atual.");
        }

        previous.setResetDone(true);
        previous.setResetDate(LocalDateTime.now());
        jpaRepository.save(previous);

        LotSequenceEntity newEntity = new LotSequenceEntity();
        newEntity.setYear(currentYear);
        newEntity.setLastNumber(0);
        newEntity.setResetDone(false);
        newEntity.setCreatedAt(LocalDateTime.now());
        newEntity.setCompanyId(companyId);

        LotSequenceEntity saved = jpaRepository.save(newEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public boolean existsByResetDoneFalse(Long companyId) {
        return jpaRepository.existsByCompanyIdAndResetDoneFalse(companyId);
    }

    @Override
    public LotSequence save(LotSequence lotSequence) {
        LotSequenceEntity entity = mapper.toEntity(lotSequence);
        return mapper.toDomain(jpaRepository.save(entity));
    }
}
