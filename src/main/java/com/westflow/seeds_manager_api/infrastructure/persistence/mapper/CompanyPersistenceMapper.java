package com.westflow.seeds_manager_api.infrastructure.persistence.mapper;

import com.westflow.seeds_manager_api.domain.model.Company;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.CompanyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyPersistenceMapper {

    CompanyEntity toEntity(Company domain);

    default Company toDomain(CompanyEntity entity) {
        if (entity == null) return null;

        return Company.restore(
                entity.getId(),
                entity.getLegalName(),
                entity.getTradeName(),
                entity.getCnpj(),
                entity.getLogoUrl(),
                entity.getPrimaryColor(),
                entity.getSecondaryColor(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getCity(),
                entity.getState(),
                entity.getZipCode(),
                entity.getTenantCode(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getActive()
        );
    }
}
