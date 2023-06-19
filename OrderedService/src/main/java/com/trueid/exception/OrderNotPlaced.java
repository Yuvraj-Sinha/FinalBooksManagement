package com.trueid.exception;

public class OrderNotPlaced extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OrderNotPlaced(String message) {
		super(message);
	}
}
