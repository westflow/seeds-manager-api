package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInvoiceRepository extends JpaRepository<InvoiceEntity,Long> {
}
