package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.response.CompanyResponse;
import com.westflow.seeds_manager_api.domain.model.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyResponse toResponse(Company company);
}
