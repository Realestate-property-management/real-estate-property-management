package com.example.demo.exception;

public class S3Exception extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public S3Exception(String message) {
        super(message);
    }

    public S3Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
