package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.SeedRequest;
import com.westflow.seeds_manager_api.api.dto.response.SeedResponse;
import com.westflow.seeds_manager_api.domain.model.Seed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeedMapper {

    @Mapping(source = "protected", target = "isProtected")
    Seed toDomain(SeedRequest request);

    Seed toDomain(SeedRequest request, Long id);

    SeedResponse toResponse(Seed seed);
}
