package com.charu.library_management_system.exception;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DuplicateIsbnException extends RuntimeException {
    public DuplicateIsbnException(String message) {
        super(message);
    }
}
