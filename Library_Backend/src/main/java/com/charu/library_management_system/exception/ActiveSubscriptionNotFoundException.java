package com.charu.library_management_system.exception;

public class ActiveSubscriptionNotFoundException extends RuntimeException {
    public ActiveSubscriptionNotFoundException(String message) {
        super(message);
    }
}
