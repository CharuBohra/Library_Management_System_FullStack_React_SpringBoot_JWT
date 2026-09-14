package com.charu.library_management_system.exception;

public class BookAlreadyAvailableException extends RuntimeException {
    public BookAlreadyAvailableException(String message) {
        super(message);
    }
}
