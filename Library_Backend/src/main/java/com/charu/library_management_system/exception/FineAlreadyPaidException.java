package com.charu.library_management_system.exception;

public class FineAlreadyPaidException extends RuntimeException {
    public FineAlreadyPaidException(String message) {
        super(message);
    }
}
