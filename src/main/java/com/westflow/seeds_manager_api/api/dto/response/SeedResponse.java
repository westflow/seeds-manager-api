package com.westflow.seeds_manager_api.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeedResponse {

    private final Long id;
    private final String species;
    private final String cultivar;
}
