package com.tcs.ilp.servease.exception;

public class InvalidServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidServiceException(String message) {
        super(message);
    }
}