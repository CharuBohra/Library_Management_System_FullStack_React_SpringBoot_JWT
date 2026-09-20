package com.charu.library_management_system.exception;

public class BookAlreadyInWishlistException extends RuntimeException {
    public BookAlreadyInWishlistException(String message) {
        super(message);
    }
}
