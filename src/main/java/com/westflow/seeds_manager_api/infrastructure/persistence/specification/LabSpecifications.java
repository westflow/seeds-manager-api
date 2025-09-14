package com.westflow.seeds_manager_api.infrastructure.persistence.specification;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LabEntity;
import org.springframework.data.jpa.domain.Specification;

public class LabSpecifications {
    public static Specification<LabEntity> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }
}
