package com.charu.library_management_system.exception;

public class ReviewNotOwnedByCurrentUserException extends RuntimeException {
    public ReviewNotOwnedByCurrentUserException(String message) {
        super(message);
    }
}
