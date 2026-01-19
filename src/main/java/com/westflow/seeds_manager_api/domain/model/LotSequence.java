package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LotSequence {

    private Long id;
    private Integer year;
    private Integer lastNumber;
    private boolean resetDone;
    private LocalDateTime resetDate;
    private LocalDateTime createdAt;

    public static LotSequence newLotSequence(
            Integer year,
            Integer lastNumber,
            boolean resetDone,
            LocalDateTime resetDate
    ) {
        validate(year, lastNumber);

        LotSequence sequence = new LotSequence();
        sequence.id = null;
        sequence.year = year;
        sequence.lastNumber = lastNumber;
        sequence.resetDone = resetDone;
        sequence.resetDate = resetDate;
        sequence.createdAt = LocalDateTime.now();
        return sequence;
    }

    public static LotSequence restore(
            Long id,
            Integer year,
            Integer lastNumber,
            boolean resetDone,
            LocalDateTime resetDate,
            LocalDateTime createdAt
    ) {
        validate(year, lastNumber);

        LotSequence sequence = new LotSequence();
        sequence.id = id;
        sequence.year = year;
        sequence.lastNumber = lastNumber;
        sequence.resetDone = resetDone;
        sequence.resetDate = resetDate;
        sequence.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        return sequence;
    }

    private static void validate(Integer year, Integer lastNumber) {
        if (year == null || year < 2000) {
            throw new ValidationException("Ano inválido ou não informado");
        }

        if (lastNumber == null || lastNumber < 0) {
            throw new ValidationException("Último número deve ser não negativo");
        }
    }
}