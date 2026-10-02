package com.charu.library_management_system.exception;

public class LoanPeriodExceededException extends RuntimeException {
    public LoanPeriodExceededException(String message) {
        super(message);
    }
}
