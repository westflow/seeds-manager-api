package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Company {

    private Long id;
    private String legalName;
    private String tradeName;
    private String cnpj;
    private String logoUrl;
    private String primaryColor;
    private String secondaryColor;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String tenantCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active = true;

    public static Company newCompany(String legalName,
                                     String tradeName,
                                     String cnpj,
                                     String email,
                                     String phone,
                                     String address,
                                     String city,
                                     String state,
                                     String zipCode,
                                     String tenantCode) {
        validate(legalName, tenantCode);
        Company c = new Company();
        c.legalName = legalName;
        c.tradeName = tradeName;
        c.cnpj = cnpj;
        c.email = email;
        c.phone = phone;
        c.address = address;
        c.city = city;
        c.state = state;
        c.zipCode = zipCode;
        c.tenantCode = tenantCode;
        c.createdAt = LocalDateTime.now();
        c.updatedAt = LocalDateTime.now();
        c.active = true;
        return c;
    }

    public static Company restore(Long id,
                                  String legalName,
                                  String tradeName,
                                  String cnpj,
                                  String logoUrl,
                                  String primaryColor,
                                  String secondaryColor,
                                  String email,
                                  String phone,
                                  String address,
                                  String city,
                                  String state,
                                  String zipCode,
                                  String tenantCode,
                                  LocalDateTime createdAt,
                                  LocalDateTime updatedAt,
                                  Boolean active) {
        return new Company(
                id,
                legalName,
                tradeName,
                cnpj,
                logoUrl,
                primaryColor,
                secondaryColor,
                email,
                phone,
                address,
                city,
                state,
                zipCode,
                tenantCode,
                createdAt,
                updatedAt,
                active
        );
    }

    private static void validate(String legalName, String tenantCode) {
        if (legalName == null || legalName.isBlank()) {
            throw new ValidationException("Razão social é obrigatória");
        }
        if (tenantCode == null || tenantCode.isBlank()) {
            throw new ValidationException("tenant_code é obrigatório");
        }
    }

    public void update(String legalName,
                       String tradeName,
                       String email,
                       String phone,
                       String address,
                       String city,
                       String state,
                       String zipCode,
                       String logoUrl,
                       String primaryColor,
                       String secondaryColor) {

        if (legalName == null || legalName.isBlank()) {
            throw new ValidationException("Razão social é obrigatória");
        }

        this.legalName = legalName;
        this.tradeName = tradeName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.logoUrl = logoUrl;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        if (Boolean.FALSE.equals(this.active)) {
            throw new ValidationException("Empresa já está deletada");
        }
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }
}
