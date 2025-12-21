package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.response.SeedResponse;
import com.westflow.seeds_manager_api.domain.model.Seed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeedMapper {

    SeedResponse toResponse(Seed seed);
}
