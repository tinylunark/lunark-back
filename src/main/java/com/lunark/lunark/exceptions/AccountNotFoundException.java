package com.lunark.lunark.exceptions;

public class AccountNotFoundException extends RuntimeException {
    AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
