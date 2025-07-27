package com.westflow.seeds_manager_api.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeedResponse {

    private Long id;
    private String species;
    private String cultivar;
    private boolean isProtected;
}
