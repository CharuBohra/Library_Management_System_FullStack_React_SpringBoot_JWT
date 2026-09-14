package com.charu.library_management_system.exception;

public class UserAlreadyHasBookLoanException extends RuntimeException {
    public UserAlreadyHasBookLoanException(String message) {
        super(message);
    }
}
