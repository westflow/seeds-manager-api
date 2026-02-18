package com.westflow.seeds_manager_api.application.usecase.company;

import com.westflow.seeds_manager_api.domain.enums.TenantRole;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import com.westflow.seeds_manager_api.domain.model.Company;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.repository.CompanyRepository;
import com.westflow.seeds_manager_api.domain.repository.UserRepository;
import com.westflow.seeds_manager_api.application.usecase.user.RegisterUserUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterCompanyUseCase {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final RegisterUserUseCase registerUserUseCase;

    @Transactional
    public Company execute(Company company,
                           String ownerEmail,
                           String ownerPassword,
                           String ownerName,
                           String ownerPosition) {

        String normalizedTenantCode = normalizeTenantCode(company.getTenantCode());
        String normalizedCnpj = normalizeCnpj(company.getCnpj());

        validateTenantCode(normalizedTenantCode);
        validateCnpj(normalizedCnpj);

        if (companyRepository.existsByTenantCode(normalizedTenantCode)) {
            throw new ValidationException("tenant_code já está em uso");
        }

        if (companyRepository.existsByCnpj(normalizedCnpj)) {
            throw new ValidationException("CNPJ já está em uso");
        }

        if (userRepository.existsByEmail(ownerEmail)) {
            throw new ValidationException("Email do OWNER já está em uso");
        }

        Company normalizedCompany = Company.newCompany(
                company.getLegalName(),
                company.getTradeName(),
                normalizedCnpj,
                company.getEmail(),
                company.getPhone(),
                company.getAddress(),
                company.getCity(),
                company.getState(),
                company.getZipCode(),
                normalizedTenantCode
        );

        Company savedCompany = companyRepository.save(normalizedCompany);

        User owner = User.newUser(
                ownerEmail,
                ownerPassword,
                ownerName,
                ownerPosition,
                TenantRole.OWNER,
                null
        ).withCompanyId(savedCompany.getId());

        registerUserUseCase.execute(owner);

        return savedCompany;
    }

    private String normalizeTenantCode(String tenantCode) {
        if (tenantCode == null) return null;
        return tenantCode.trim().toLowerCase();
    }

    private String normalizeCnpj(String cnpj) {
        if (cnpj == null) return null;
        return cnpj.replaceAll("[^0-9]", "");
    }

    private void validateTenantCode(String tenantCode) {
        if (tenantCode == null || tenantCode.isBlank()) {
            throw new ValidationException("tenant_code é obrigatório");
        }
        if (!tenantCode.matches("^[a-z0-9]+$")) {
            throw new ValidationException("tenant_code deve conter apenas letras minúsculas e números, sem espaços");
        }
        if (tenantCode.length() < 3 || tenantCode.length() > 50) {
            throw new ValidationException("tenant_code deve ter entre 3 e 50 caracteres");
        }
    }

    private void validateCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new ValidationException("CNPJ é obrigatório");
        }

        if (cnpj.length() != 14) {
            throw new ValidationException("CNPJ deve conter 14 dígitos");
        }

        if (!cnpj.matches("\\d{14}")) {
            throw new ValidationException("CNPJ deve conter apenas dígitos");
        }

        if (cnpj.chars().distinct().count() == 1) {
            throw new ValidationException("CNPJ inválido");
        }

        if (!isValidCnpjDigits(cnpj)) {
            throw new ValidationException("CNPJ inválido");
        }
    }

    private boolean isValidCnpjDigits(String cnpj) {
        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                int num = Character.getNumericValue(cnpj.charAt(i));
                sum += num * weights1[i];
            }
            int remainder = sum % 11;
            char dv1 = (remainder < 2) ? '0' : (char) ((11 - remainder) + '0');

            sum = 0;
            for (int i = 0; i < 13; i++) {
                int num = Character.getNumericValue(cnpj.charAt(i));
                sum += num * weights2[i];
            }
            remainder = sum % 11;
            char dv2 = (remainder < 2) ? '0' : (char) ((11 - remainder) + '0');

            return dv1 == cnpj.charAt(12) && dv2 == cnpj.charAt(13);
        } catch (Exception e) {
            return false;
        }
    }
}
