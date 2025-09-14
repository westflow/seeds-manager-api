package com.westflow.seeds_manager_api.api.exception;

import java.util.List;

public class ValidationErrorResponse {
    private final int status;
    private final String message;
    private final List<String> errors;
    private final String path;

    public ValidationErrorResponse(int status, String message, List<String> errors, String path) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.path = path;
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public List<String> getErrors() { return errors; }
    public String getPath() { return path; }
}

