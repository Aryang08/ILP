package com.tcs.ilp.servease.exception;

public class ForeignKeyViolationException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	public ForeignKeyViolationException(String message) {
		super(message);
	}

}
