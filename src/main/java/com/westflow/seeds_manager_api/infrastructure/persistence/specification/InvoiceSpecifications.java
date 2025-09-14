package com.westflow.seeds_manager_api.infrastructure.persistence.specification;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.InvoiceEntity;
import org.springframework.data.jpa.domain.Specification;

public class InvoiceSpecifications {
    public static Specification<InvoiceEntity> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }
}
