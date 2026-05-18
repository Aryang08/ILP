package com.tcs.ilp.servease.exception;

public class ServiceHistoryException extends Exception {

    public ServiceHistoryException(String message) {
        super(message);
    }

    public ServiceHistoryException(String message, Throwable cause) {
        super(message, cause);
    }
}