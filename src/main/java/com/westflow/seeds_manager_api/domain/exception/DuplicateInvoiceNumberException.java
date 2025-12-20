package com.westflow.seeds_manager_api.domain.exception;

public class DuplicateInvoiceNumberException extends BusinessException  {
    public DuplicateInvoiceNumberException(String invoiceNumber) {
        super(String.format("Nota fiscal com número [%s] já está registrada.", invoiceNumber));
    }
}
