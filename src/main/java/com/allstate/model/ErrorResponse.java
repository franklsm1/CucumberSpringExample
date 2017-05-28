package com.allstate.model;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorResponse extends AbstractResponse {
    private final List<FieldError> errors;

    public ErrorResponse(List<FieldError> errors, HttpStatus httpStatus) {
        this.errors = errors;
        this.setStatus(httpStatus.value());
        this.setMessage(httpStatus.getReasonPhrase());
    }

    public List<FieldError> getErrors() {
        return errors;
    }
}
