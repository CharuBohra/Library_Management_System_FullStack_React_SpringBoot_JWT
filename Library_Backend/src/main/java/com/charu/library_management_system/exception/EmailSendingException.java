package com.charu.library_management_system.exception;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String failedToSendEmail) {
        super(failedToSendEmail);
    }
}
