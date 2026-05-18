package com.tcs.ilp.servease.exception;

public class BillUserException extends RuntimeException {

    public BillUserException(String message) {
        super(message);
    }

    public BillUserException(String message, Throwable cause) {
        super(message, cause);
    }
}