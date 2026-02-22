package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.LotSequence;

public interface LotSequenceRepository {
    String generateFormattedLotNumber(Long companyId);
    LotSequence resetPreviousAndCreateCurrent(Long companyId);
    boolean existsByResetDoneFalse(Long companyId);
    LotSequence save(LotSequence lotSequence);
}
