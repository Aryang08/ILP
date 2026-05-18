package com.tcs.ilp.servease.exception;

public class DataAccessException extends DAOException {
	private static final long serialVersionUID = 1L;
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}