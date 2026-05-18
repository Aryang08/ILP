
package com.tcs.ilp.servease.exception;

public class DiagnosticException extends Exception {

    // Default Constructor
    public DiagnosticException() {
        super();
    }

    // Constructor with message
    public DiagnosticException(String message) {
        super(message);
    }

    // Constructor with message + cause
    public DiagnosticException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public DiagnosticException(Throwable cause) {
        super(cause);
    }
}