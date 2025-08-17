package com.westflow.seeds_manager_api.domain.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seed {
    private Long id;
    private String species;
    private String cultivar;
    private boolean isProtected;
}
