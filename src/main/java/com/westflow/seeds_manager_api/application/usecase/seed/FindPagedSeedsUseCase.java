package com.westflow.seeds_manager_api.application.usecase.seed;

import com.westflow.seeds_manager_api.domain.model.Seed;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPagedSeedsUseCase {

    private final SeedRepository seedRepository;

    public Page<Seed> execute(Boolean isProtected, Pageable pageable) {
        return seedRepository.findAll(isProtected, pageable);
    }
}
