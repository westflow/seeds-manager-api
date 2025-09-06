package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLotInvoiceRepository extends JpaRepository<LotInvoiceEntity,Long> {
}
