package com.charu.library_management_system.exception;

public class MaxReservationLimitException extends RuntimeException {
    public MaxReservationLimitException(String message) {
        super(message);
    }
}
