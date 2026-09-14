package com.charu.library_management_system.exception;

public class ReservationCannotBeCancelledException extends RuntimeException {
    public ReservationCannotBeCancelledException(String message) {
        super(message);
    }
}
