package com.westflow.seeds_manager_api.domain.entity;


import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LotSequence {

    private final Long id;
    private final Integer year;
    private final Integer lastNumber;
    private final boolean resetDone;
    private final LocalDateTime resetDate;
    private final LocalDateTime createdAt;

    @Builder
    public LotSequence(Long id, Integer year, Integer lastNumber, boolean resetDone,
                       LocalDateTime resetDate, LocalDateTime createdAt) {
        validate(year, lastNumber);
        this.id = id;
        this.year = year;
        this.lastNumber = lastNumber;
        this.resetDone = resetDone;
        this.resetDate = resetDate;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    private void validate(Integer year, Integer lastNumber) {
        if (year == null || year < 2000) {
            throw new ValidationException("Ano inválido ou não informado");
        }

        if (lastNumber == null || lastNumber < 0) {
            throw new ValidationException("Último número deve ser não negativo");
        }
    }

    public LotSequence increment() {
        return LotSequence.builder()
                .id(this.id)
                .year(this.year)
                .lastNumber(this.lastNumber + 1)
                .resetDone(this.resetDone)
                .resetDate(this.resetDate)
                .createdAt(this.createdAt)
                .build();
    }

    public LotSequence reset() {
        return LotSequence.builder()
                .id(this.id)
                .year(this.year)
                .lastNumber(0)
                .resetDone(true)
                .resetDate(LocalDateTime.now())
                .createdAt(this.createdAt)
                .build();
    }
}