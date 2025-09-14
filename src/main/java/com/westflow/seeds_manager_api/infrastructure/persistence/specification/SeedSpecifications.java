package com.westflow.seeds_manager_api.infrastructure.persistence.specification;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.SeedEntity;
import org.springframework.data.jpa.domain.Specification;

public class SeedSpecifications {
    public static Specification<SeedEntity> hasProtected(Boolean isProtected) {
        return (root, query, cb) -> {
            if (isProtected == null) return null;
            return cb.equal(root.get("isProtected"), isProtected);
        };
    }

    public static Specification<SeedEntity> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

    public static Specification<SeedEntity> filter(Boolean isProtected) {
        Specification<SeedEntity> spec = isActive();
        if (isProtected != null) {
            spec = spec.and(hasProtected(isProtected));
        }
        return spec;
    }
}
