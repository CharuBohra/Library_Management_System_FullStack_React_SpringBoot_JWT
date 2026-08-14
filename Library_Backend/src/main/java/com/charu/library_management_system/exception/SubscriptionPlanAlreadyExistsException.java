package com.charu.library_management_system.exception;

public class SubscriptionPlanAlreadyExistsException extends RuntimeException {
    public SubscriptionPlanAlreadyExistsException(String message) {
        super(message);
    }
}
