package com.trueid.exception;

public class UserAlreadyExist extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyExist() {
		super("User Already Exist");

	}

	public UserAlreadyExist(String message) {
		super(message);
	}
}
