package com.westflow.seeds_manager_api.domain.exception;

public class InsufficientLotBalanceException extends DomainException  {
    public InsufficientLotBalanceException(String lotNumber, double available, double requested) {
        super(String.format(
                "Saldo insuficiente para o lote %s. Disponível: %.2f kg, solicitado: %.2f kg.",
                lotNumber, available, requested
        ));
    }
}
