package com.charu.library_management_system.exception;

public class BookCannotBeRenewedException extends RuntimeException {
    public BookCannotBeRenewedException(String message) {
        super(message);
    }
}
