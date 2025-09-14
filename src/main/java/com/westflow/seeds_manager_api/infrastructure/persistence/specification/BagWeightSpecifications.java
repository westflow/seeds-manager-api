package com.westflow.seeds_manager_api.infrastructure.persistence.specification;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagWeightEntity;
import org.springframework.data.jpa.domain.Specification;

public class BagWeightSpecifications {
    public static Specification<BagWeightEntity> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }
}
