package com.westflow.seeds_manager_api.infrastructure.persistence.specification;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagTypeEntity;
import org.springframework.data.jpa.domain.Specification;

public class BagTypeSpecifications {
    public static Specification<BagTypeEntity> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }
}
