package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BagWeight {
    private final Long id;
    private final BigDecimal weight;

    @Builder
    public BagWeight(Long id, BigDecimal weight) {
        validate(weight);
        this.id = id;
        this.weight = weight;
    }

    private void validate(BigDecimal weight) {
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Peso da sacaria deve ser maior que zero");
        }
    }
}
