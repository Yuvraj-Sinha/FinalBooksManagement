package com.trueid.gateway.exception;

public class UnAuthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnAuthorizedException() {
		super("Unauthorized to Access");

	}

	public UnAuthorizedException(String message) {
		super(message);

	}

}
