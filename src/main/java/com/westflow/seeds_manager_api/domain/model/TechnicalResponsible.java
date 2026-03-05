package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import com.westflow.seeds_manager_api.domain.util.CPFUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TechnicalResponsible {

    private Long id;
    private Long companyId;
    private String name;
    private String cpf;
    private String renasemNumber;
    private String creaNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phone;
    private String email;
    private Boolean isPrimary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;

    public static TechnicalResponsible newTechnicalResponsible(
            Long companyId,
            String name,
            String cpf,
            String renasemNumber,
            String creaNumber,
            String address,
            String city,
            String state,
            String zipCode,
            String phone,
            String email,
            Boolean isPrimary
    ) {
        validateRequired(companyId, name, cpf);

        TechnicalResponsible tr = new TechnicalResponsible();
        tr.id = null;
        tr.companyId = companyId;
        tr.name = name.trim();
        tr.cpf = CPFUtils.normalize(cpf);
        tr.renasemNumber = renasemNumber;
        tr.creaNumber = creaNumber;
        tr.address = address;
        tr.city = city;
        tr.state = state;
        tr.zipCode = zipCode;
        tr.phone = phone;
        tr.email = email;
        tr.isPrimary = Boolean.TRUE.equals(isPrimary);
        tr.createdAt = LocalDateTime.now();
        tr.updatedAt = null;
        tr.active = true;
        return tr;
    }

    public static TechnicalResponsible restore(
            Long id,
            Long companyId,
            String name,
            String cpf,
            String renasemNumber,
            String creaNumber,
            String address,
            String city,
            String state,
            String zipCode,
            String phone,
            String email,
            Boolean isPrimary,
            java.time.LocalDateTime createdAt,
            java.time.LocalDateTime updatedAt,
            Boolean active
    ) {
        validateRequired(companyId, name, cpf);

        return new TechnicalResponsible(
                id,
                companyId,
                name,
                cpf,
                renasemNumber,
                creaNumber,
                address,
                city,
                state,
                zipCode,
                phone,
                email,
                Boolean.TRUE.equals(isPrimary),
                createdAt,
                updatedAt,
                Boolean.TRUE.equals(active)
        );
    }

    public void update(
            String name,
            String renasemNumber,
            String creaNumber,
            String address,
            String city,
            String state,
            String zipCode,
            String phone,
            String email,
            Boolean isPrimary
    ) {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new BusinessException("Responsável técnico está inativo e não pode ser atualizado.");
        }

        if (name != null && !name.isBlank()) {
            this.name = name.trim();
        }

        this.renasemNumber = renasemNumber;
        this.creaNumber = creaNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phone = phone;
        this.email = email;
        this.isPrimary = Boolean.TRUE.equals(isPrimary);
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new BusinessException("Responsável técnico já está inativo.");
        }
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsPrimary() {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new BusinessException("Responsável técnico inativo não pode ser marcado como principal.");
        }
        this.isPrimary = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void unmarkPrimary() {
        this.isPrimary = false;
        this.updatedAt = LocalDateTime.now();
    }

    private static void validateRequired(Long companyId, String name, String cpf) {
        if (companyId == null) {
            throw new ValidationException("companyId is required");
        }
        if (name == null || name.isBlank()) {
            throw new ValidationException("Name is required");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new ValidationException("CPF is required");
        }
    }
}
