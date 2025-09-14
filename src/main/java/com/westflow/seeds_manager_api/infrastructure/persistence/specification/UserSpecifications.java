package com.westflow.seeds_manager_api.infrastructure.persistence.specification;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<UserEntity> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }
}
