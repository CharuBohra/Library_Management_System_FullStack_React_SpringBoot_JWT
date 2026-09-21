package com.charu.library_management_system.exception;

public class BookAlreadyReviewedException extends RuntimeException {
    public BookAlreadyReviewedException(String message) {
        super(message);
    }
}
