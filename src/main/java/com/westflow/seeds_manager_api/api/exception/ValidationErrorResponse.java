package com.westflow.seeds_manager_api.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationErrorResponse {

    private int status;
    private String message;
    private List<String> errors;
}
