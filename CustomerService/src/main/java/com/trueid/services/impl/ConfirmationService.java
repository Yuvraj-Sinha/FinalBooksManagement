package com.trueid.services.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
@Getter
public class ConfirmationService {

	static boolean confirmation = false;

	@Async
	public CompletableFuture<Boolean> getUserConfirmation() throws Exception {

		Thread.sleep(20000);
		boolean confirmationResult = confirmation;
		confirmation = false;
		// Return the result as a CompletableFuture
		return CompletableFuture.completedFuture(confirmationResult);
	}

	public boolean getUserConfiramtion(boolean value) {
		confirmation = false;
		if (value == true) {
			confirmation = true;
		}
		return confirmation;
	}

}
