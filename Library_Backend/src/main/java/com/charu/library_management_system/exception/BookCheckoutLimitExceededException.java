package com.charu.library_management_system.exception;

public class BookCheckoutLimitExceededException extends RuntimeException {
    public BookCheckoutLimitExceededException(String message) {
        super(message);
    }
}
