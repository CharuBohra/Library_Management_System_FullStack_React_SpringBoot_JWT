package com.charu.library_management_system.exception;

public class UserAlreadyHasReservationException extends  RuntimeException{
    public UserAlreadyHasReservationException(String message)
    {
        super(message);
    }
}
