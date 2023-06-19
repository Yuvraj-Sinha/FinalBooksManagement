package com.trueid.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	@GetMapping("/homes")
	public String getMsg() {
		return "yuvraj";
	}
}
