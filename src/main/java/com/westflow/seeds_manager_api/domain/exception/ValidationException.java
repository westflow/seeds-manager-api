package com.westflow.seeds_manager_api.domain.exception;

public class ValidationException extends DomainException  {
    public ValidationException(String message) {
        super("Validação falhou: " + message);
    }
}
