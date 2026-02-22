package com.westflow.seeds_manager_api.infrastructure.persistence.specification;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.TechnicalResponsibleEntity;
import org.springframework.data.jpa.domain.Specification;

public class TechnicalResponsibleSpecifications {

    public static Specification<TechnicalResponsibleEntity> isCompany(Long companyId) {
        return (root, query, cb) -> cb.equal(root.get("companyId"), companyId);
    }

    public static Specification<TechnicalResponsibleEntity> isActive() {
        return (root, query, cb) -> cb.equal(root.get("active"), true);
    }
}

