package com.trueid.exception;

public class OrderNotConfirmedException extends RuntimeException {
	public OrderNotConfirmedException(String message) {
		super(message);
	}
}
