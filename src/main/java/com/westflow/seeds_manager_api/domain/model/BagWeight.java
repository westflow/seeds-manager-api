package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BagWeight {

    private Long id;
    private BigDecimal weight;
    private Boolean active;

    public static BagWeight newBagWeight(BigDecimal weight) {
        validate(weight);

        BagWeight bagWeight = new BagWeight();
        bagWeight.id = null;
        bagWeight.weight = weight;
        bagWeight.active = true;
        return bagWeight;
    }

    public static BagWeight restore(Long id, BigDecimal weight, Boolean active) {
        validate(weight);

        return new BagWeight(id, weight, active);
    }

    public void update(BigDecimal weight) {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new BusinessException("Peso de sacaria está inativo e não pode ser atualizado.");
        }

        validate(weight);
        this.weight = weight;
    }

    public void deactivate() {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new ValidationException("O peso da sacaria já está deletado.");
        }
        this.active = false;
    }

    private static void validate(BigDecimal weight) {
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Peso da sacaria deve ser maior que zero");
        }
    }
}
