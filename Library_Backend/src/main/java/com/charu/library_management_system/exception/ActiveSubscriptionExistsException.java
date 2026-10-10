package com.charu.library_management_system.exception;

public class ActiveSubscriptionExistsException extends RuntimeException {
    public ActiveSubscriptionExistsException(String message) {
        super(message);
    }
}
