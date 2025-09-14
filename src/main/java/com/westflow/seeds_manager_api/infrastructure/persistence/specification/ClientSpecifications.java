package com.westflow.seeds_manager_api.infrastructure.persistence.specification;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.ClientEntity;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecifications {
    public static Specification<ClientEntity> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }
}
