package com.lunark.lunark.moderation.exception;

public class UnauthorizedReportException extends RuntimeException {
    public UnauthorizedReportException(String message) {
        super(message);
    }
}
