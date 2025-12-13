package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.LotSequence;

public interface LotSequenceRepository {
    String generateFormattedLotNumber();
    LotSequence resetPreviousAndCreateCurrent();
    boolean existsByResetDoneFalse();
    LotSequence save(LotSequence lotSequence);
}
