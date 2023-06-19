package com.trueid.exception;

public class UserNotFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFound() {
		super("resource not found on server");

	}

	public UserNotFound(String message) {
		super(message);

	}

}
