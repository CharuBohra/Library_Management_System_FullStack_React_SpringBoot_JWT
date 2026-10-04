package com.charu.library_management_system.exception;

public class BookHasLoanHistoryException extends RuntimeException {
    public BookHasLoanHistoryException(String message) {
        super(message);
    }
}
