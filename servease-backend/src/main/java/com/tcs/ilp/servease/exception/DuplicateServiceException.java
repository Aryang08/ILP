package com.tcs.ilp.servease.exception;

public class DuplicateServiceException extends DAOException {
    public DuplicateServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}