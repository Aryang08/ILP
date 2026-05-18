package com.tcs.ilp.servease.exception;
/**
 * Base exception for DAO layer
 */
public class DAOException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    public DAOException(String message) {
        super(message);
    }
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}