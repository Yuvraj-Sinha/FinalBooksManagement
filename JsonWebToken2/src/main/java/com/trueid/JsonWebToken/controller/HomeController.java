package com.trueid.JsonWebToken.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/home")
	public ResponseEntity<?> getMsg() {
		String text = "this is private page";
		text += "this is not allowed to unauthenticated users";
		return ResponseEntity.status(HttpStatus.OK).body(text);
	}

}
