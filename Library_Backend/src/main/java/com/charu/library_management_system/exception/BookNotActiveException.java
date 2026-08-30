package com.charu.library_management_system.exception;

public class BookNotActiveException extends RuntimeException {
    public BookNotActiveException(String message) {
        super(message);
    }
}
