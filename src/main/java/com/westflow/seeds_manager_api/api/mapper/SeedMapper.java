package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.SeedCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.SeedResponse;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeedMapper {

    @Mapping(source = "protected", target = "isProtected")
    Seed toDomain(SeedCreateRequest request);

    SeedResponse toResponse(Seed seed);
}
