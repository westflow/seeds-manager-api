package com.westflow.seeds_manager_api.domain.exception;

public class BusinessException extends DomainException {
  public BusinessException(String message) {
    super("Regra de negócio violada: " + message);
  }
}
