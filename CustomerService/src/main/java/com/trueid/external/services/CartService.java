package com.trueid.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.trueid.custom.response.ApiResponse;
import com.trueid.dto.Cart;

@Service
@FeignClient(name = "CART-SERVICE")
public interface CartService {
	@GetMapping("/cart/addBook/{id}/username/{username}")
	public ApiResponse<Cart> addToCart(@PathVariable(name = "id") Long id,
			@PathVariable(name = "username") String username);

	@GetMapping("/cart/getCart/{username}")
	public Cart getCart(@PathVariable(name = "username") String username);

	@GetMapping("cart/placeOrder/{username}")
	public ApiResponse<String> placeOrder(@PathVariable(name = "username") String username);

	@GetMapping("cart/deleteOrder/{id}/username/{username}")
	public boolean deletedOrderFromCart(@PathVariable(name = "id") Long id,
			@PathVariable(name = "username") String username);
}
