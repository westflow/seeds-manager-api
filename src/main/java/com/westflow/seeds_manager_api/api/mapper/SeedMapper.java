package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.SeedCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.SeedResponse;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeedMapper {

    Seed toDomain(SeedCreateRequest request);

    SeedResponse toResponse(Seed seed);
}
