package com.charu.library_management_system.exception;

public class UnpaidFinesExistsException extends RuntimeException {
    public UnpaidFinesExistsException(String message) {
        super(message);
    }
}
