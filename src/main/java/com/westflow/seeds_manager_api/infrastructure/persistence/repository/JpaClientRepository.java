package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClientRepository extends JpaRepository<ClientEntity, Long> {
}
