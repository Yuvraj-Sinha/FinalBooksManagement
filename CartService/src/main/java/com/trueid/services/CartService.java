package com.trueid.services;

import com.trueid.custom.response.ApiResponse;

public interface CartService {
	public ApiResponse<String> orderPlace(String username);

}
