package com.example.demo.exception;

public class LeaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LeaseException(String message) {
        super(message);
    }

    public LeaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
