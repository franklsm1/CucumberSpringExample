package com.example.model;

import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorResponse {
    private final List<FieldError> errors;

    public ErrorResponse(List<FieldError> errors) {
        this.errors = errors;
    }

    public List<FieldError> getErrors() {
        return errors;
    }
}
