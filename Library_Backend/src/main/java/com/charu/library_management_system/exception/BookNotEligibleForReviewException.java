package com.charu.library_management_system.exception;

public class BookNotEligibleForReviewException extends RuntimeException {
    public BookNotEligibleForReviewException(String message) {
        super(message);
    }
}
