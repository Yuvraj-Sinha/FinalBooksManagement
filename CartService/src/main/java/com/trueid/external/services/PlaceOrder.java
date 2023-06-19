package com.trueid.external.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.trueid.custom.response.ApiResponse;

@Service
@FeignClient(name = "ORDERED-SERVICE")
public interface PlaceOrder {
	@PostMapping("/ordered/items/{username}")

	public ApiResponse<String> placeOrder(@PathVariable(name = "username") String username,
			@RequestBody List<Long> bookIds);

}
