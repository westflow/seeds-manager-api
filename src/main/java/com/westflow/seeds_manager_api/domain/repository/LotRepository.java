package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.Lot;

public interface LotRepository {
    Lot save(Lot lot);
}
