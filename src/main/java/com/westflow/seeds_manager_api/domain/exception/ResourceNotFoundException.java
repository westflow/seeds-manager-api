package com.westflow.seeds_manager_api.domain.exception;

public class ResourceNotFoundException extends DomainException  {
    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(String.format("%s com identificador [%s] não encontrado.", resourceName, identifier));
    }
}
