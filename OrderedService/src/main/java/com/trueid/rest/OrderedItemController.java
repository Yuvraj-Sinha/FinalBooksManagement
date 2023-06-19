package com.trueid.rest;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trueid.custom.response.ApiResponse;
import com.trueid.entity.OrderItem;
import com.trueid.exception.OrderNotPlaced;
import com.trueid.repo.OrderedItemRepo;
import com.trueid.service.OrderedItemService;

import feign.FeignException;

@RestController
@RequestMapping("/ordered")
public class OrderedItemController {
	@Autowired
	OrderedItemService orderService;
	@Autowired
	OrderedItemRepo orderRepo;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/items/{username}")
	public ApiResponse<String> placeOrder(@PathVariable String username, @RequestBody List<Long> bookIds) {
		System.out.println(bookIds + " username" + username);

		ApiResponse<String> placeOrder = null;
		String order = null;
		try {
			order = orderService.placeOrder(username, bookIds);
		} catch (FeignException.NotFound e) {
			java.nio.ByteBuffer byteBuffer = null; // The ByteBuffer you want to convert to a String
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
				int intValue = jsonNode.get("status").intValue();
				stat = HttpStatus.valueOf(intValue);
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

			placeOrder = new ApiResponse(stat, message, data);
			// Retrieve the desired data from the JSON
			String jsonData = jsonNode.toString();
			System.err.println(jsonData);
			// Use the extracted JSON data as needed
			// ...
			throw new OrderNotPlaced("Book OUT OF STOCK");
		}
		placeOrder = new ApiResponse<>(HttpStatus.OK, "Order Placed Successfully", order);

		return placeOrder;
	}

	@GetMapping("/getItems/{username}")
	public List<OrderItem> getOrder(@PathVariable String username) {
		return orderRepo.findByUsername(username);
	}
}
