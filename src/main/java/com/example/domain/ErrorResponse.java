package com.example.domain;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.List;

@Data
public class ErrorResponse {
    private final List<FieldError> errors;
}
