package com.charu.library_management_system.exception;

public class SubscriptionAlreadyInactiveException extends RuntimeException {
    public SubscriptionAlreadyInactiveException(String message) {
        super(message);
    }
}
