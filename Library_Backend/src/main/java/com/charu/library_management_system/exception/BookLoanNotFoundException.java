package com.charu.library_management_system.exception;

public class BookLoanNotFoundException extends RuntimeException {
    public BookLoanNotFoundException(String message) {
        super(message);
    }
}
