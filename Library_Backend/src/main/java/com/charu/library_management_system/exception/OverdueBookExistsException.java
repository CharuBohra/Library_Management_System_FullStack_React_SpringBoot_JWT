package com.charu.library_management_system.exception;

public class OverdueBookExistsException extends RuntimeException {
    public OverdueBookExistsException(String message) {
        super(message);
    }
}
