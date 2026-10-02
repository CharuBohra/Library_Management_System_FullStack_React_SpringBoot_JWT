package com.charu.library_management_system.exception;

public class ReservationCannotBeFulfilledException extends RuntimeException {
    public ReservationCannotBeFulfilledException(String message) {
        super(message);
    }
}
