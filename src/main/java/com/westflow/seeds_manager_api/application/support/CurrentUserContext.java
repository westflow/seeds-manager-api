package com.westflow.seeds_manager_api.application.support;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserContext {

    public static Long getCompanyId() {
        return TenantContext.getTenantId();
    }

    public static boolean isSuperAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        for (GrantedAuthority ga : auth.getAuthorities()) {
            if (ga.getAuthority().equals("ROLE_SUPER_ADMIN")) return true;
        }
        return false;
    }
}

