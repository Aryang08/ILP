package com.tcs.ilp.servease.exception;

/**
 * Custom exception for Service Diagnostics module.
 * Thrown when service diagnostic data is invalid,
 * missing, or an operation cannot be completed.
 */
public class ServiceDiagnosticsException extends Exception {

    public ServiceDiagnosticsException(String message) {
        super(message);
    }

    public ServiceDiagnosticsException(String message, Throwable cause) {
        super(message, cause);
    }
}