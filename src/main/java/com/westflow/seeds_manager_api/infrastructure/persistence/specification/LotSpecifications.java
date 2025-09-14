package com.westflow.seeds_manager_api.infrastructure.persistence.specification;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotEntity;
import org.springframework.data.jpa.domain.Specification;

public class LotSpecifications {
    public static Specification<LotEntity> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }
}
