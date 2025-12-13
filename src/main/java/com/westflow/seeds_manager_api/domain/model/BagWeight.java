package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BagWeight {
    private final Long id;
    private final BigDecimal weight;
    private Boolean active = true;

    @Builder
    public BagWeight(Long id, BigDecimal weight, Boolean active) {
        validate(weight);
        this.id = id;
        this.weight = weight;
        if (active != null) {
            this.active = active;
        }
    }

    private void validate(BigDecimal weight) {
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Peso da sacaria deve ser maior que zero");
        }
    }

    public void deactivate() {
        if (!this.active) {
            throw new ValidationException("O peso da sacaria já está deletado.");
        }
        this.active = false;
    }
}
