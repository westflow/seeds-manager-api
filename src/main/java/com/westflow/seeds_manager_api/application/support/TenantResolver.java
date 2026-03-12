package com.westflow.seeds_manager_api.application.support;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;

public class TenantResolver {

    /**
     * Resolve the effective companyId for an action.
     * If the current user is SUPER_ADMIN, the optionalCompanyId must be provided (not null).
     * If the current user is not SUPER_ADMIN, the tenant from CurrentUserContext must be present.
     * Throws ValidationException with a clear message when missing.
     */
    public static Long resolveCompanyIdOrThrow(Long optionalCompanyId, String actionDescription) {
        if (CurrentUserContext.isSuperAdmin()) {
            if (optionalCompanyId == null) {
                throw new ValidationException("companyId é obrigatório para SUPER_ADMIN ao " + actionDescription);
            }
            return optionalCompanyId;
        } else {
            Long companyId = CurrentUserContext.getCompanyId();
            if (companyId == null) {
                throw new ValidationException("companyId não encontrado no contexto do usuário ao " + actionDescription);
            }
            return companyId;
        }
    }
}

