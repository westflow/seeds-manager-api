package com.westflow.seeds_manager_api.domain.util;

public final class CPFUtils {

    private CPFUtils() {
    }

    public static String normalize(String cpf) {
        if (cpf == null) return null;
        return cpf.trim().replaceAll("\\D", "");
    }
}

