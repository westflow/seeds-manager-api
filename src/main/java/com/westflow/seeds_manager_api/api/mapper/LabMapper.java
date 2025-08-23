package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.LabCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.LabResponse;
import com.westflow.seeds_manager_api.domain.entity.Lab;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabMapper {

    Lab toDomain(LabCreateRequest request);

    LabResponse toResponse(Lab lab);
}
