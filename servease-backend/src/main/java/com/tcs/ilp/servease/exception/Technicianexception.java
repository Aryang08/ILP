package com.tcs.ilp.servease.exception;

/**
 * Universal exception class for ServeEase application.
 * Used across Technician, Specialization, TechSkills and other modules.
 */
public class Technicianexception extends RuntimeException {   // ✅ changed

    private static final long serialVersionUID = 1L;

    /**
     * Error categories to classify failures across the project.
     */
    public enum ErrorCode {

        // ---- INPUT / VALIDATION ERRORS ----
        INVALID_INPUT,

        // ---- LOOKUP / FETCH ERRORS ----
        TECHNICIAN_NOT_FOUND,
        NOT_FOUND,

        // ---- DUPLICATE ERRORS ----
        TECHNICIAN_ALREADY_EXISTS,
        ALREADY_EXISTS,

        // ---- DATABASE ERRORS ----
        DATABASE_ERROR
    }

    private final ErrorCode errorCode;

    // ✅ Constructor
    public Technicianexception(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    // ✅ Constructor with cause
    public Technicianexception(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    // ✅ Getter
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}