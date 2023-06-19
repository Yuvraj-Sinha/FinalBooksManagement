package com.trueid.services.impl;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trueid.custom.response.ApiResponse;
import com.trueid.entity.Cart;
import com.trueid.entity.Order;
import com.trueid.external.services.PlaceOrder;
import com.trueid.repo.CartRepo;
import com.trueid.repo.OrderRepo;
import com.trueid.services.CartService;

import feign.FeignException;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	CartRepo cartRepo;
	@Autowired
	PlaceOrder placeOrder;
	@Autowired
	OrderRepo orderRepo;

	@Override

	public ApiResponse<String> orderPlace(String username) {

		Cart cart = cartRepo.findByUsername(username);
		List<Order> order = cart.getOrder();
		List<Long> bookIds = new ArrayList<>();
		if (order.size() == 0) {

			return null;

		} else {
			for (Order ordr : order) {
				bookIds.add(ordr.getId());
			}
		}

		ApiResponse<String> order2 = null;
		String string;

		try {
			order2 = placeOrder.placeOrder(username, bookIds);

			string = order2.getData();

		} catch (FeignException.NotFound e) {

			ByteBuffer byteBuffer = null; // The ByteBuffer you want to convert to a String
			Charset charset = Charset.forName("UTF-8"); // Specify the character encoding (e.g., UTF-8)

			// Extract the response body from the exception
			byteBuffer = e.responseBody().get();
			// Decode the bytes from the ByteBuffer into a String
			String str = charset.decode(byteBuffer).toString();
			// Parse the JSON response body
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = null;
			HttpStatus stat = null;
			String message = null;
			String data = null;
			try {
				jsonNode = objectMapper.readTree(str);
				// String string = jsonNode.get("status").toString();
				// System.out.println("http satatus" + string);
				stat = HttpStatus.valueOf(404);
				message = jsonNode.get("message").asText();
				JsonNode dataNode = jsonNode.get("data");
				data = objectMapper.writeValueAsString(dataNode);
			} catch (JsonMappingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Convert dataNode
			// to JSON string

			order2 = new ApiResponse(stat, message, data);
			// Retrieve the desired data from the JSON
			String jsonData = jsonNode.toString();
			System.err.println(jsonData);
			// Use the extracted JSON data as needed
			// ...
			return order2;
		}

		System.out.println("after place order service");
		try {
			orderRepo.deleteByCartId(cart.getCartId());

		} catch (Exception e) {
			e.printStackTrace();
			return order2;

		}

		ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK, "Order Placed Successfully", string);
		// order2);
		return apiResponse;
	}

}
