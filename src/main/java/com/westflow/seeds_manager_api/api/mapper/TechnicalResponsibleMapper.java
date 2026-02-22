package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.response.TechnicalResponsibleResponse;
import com.westflow.seeds_manager_api.domain.model.TechnicalResponsible;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnicalResponsibleMapper {
    TechnicalResponsibleResponse toResponse(TechnicalResponsible technicalResponsible);
}
