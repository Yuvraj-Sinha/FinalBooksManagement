package com.trueid.JsonWebToken.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("resource not found on server");

	}

	public UserNotFoundException(String message) {
		super(message);

	}

}
