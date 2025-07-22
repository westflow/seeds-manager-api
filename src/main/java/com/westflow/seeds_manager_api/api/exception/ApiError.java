package com.westflow.seeds_manager_api.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiError {

    private HttpStatus status;
    private String message;
}
