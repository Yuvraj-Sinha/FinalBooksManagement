package com.trueid.rest;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trueid.custom.response.ApiResponse;
import com.trueid.dto.BookDto;
import com.trueid.dto.Cart;
import com.trueid.exception.OrderNotConfirmedException;
import com.trueid.exception.ResourceNotFoundException;
import com.trueid.external.services.BookStore;
import com.trueid.external.services.CartService;
import com.trueid.services.impl.ConfirmationService;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/ai")
@Api(value = "Cart Controller", description = "Book/Cart REST Endpoints.")
public class CartController {
	@Autowired
	private BookStore bookStore;
	@Autowired
	private CartService cartService;
	@Autowired
	private ConfirmationService confirmationService;

	@GetMapping()
	@ApiOperation(value = "GetBooks")
	@CircuitBreaker(name = "AllServiceBreaker", fallbackMethod = "serviceFallback")
	public ResponseEntity<?> getBook() {
		List<BookDto> book = bookStore.getBook();
		return ResponseEntity.status(HttpStatus.OK).body(book);
	}

	@GetMapping("/buy")
	@ApiOperation(value = "Place Order From Cart")
	@Transactional(rollbackOn = Exception.class)
	// @CircuitBreaker(name = "AllServiceBreaker", fallbackMethod =
	// "orderServiceFallback")
	public ResponseEntity<?> buyBook(@RequestHeader("loggedInUser") String username)
			throws JsonMappingException, JsonProcessingException {
		// Book purchaseBook = bookStore.purchaseBook(bookId);
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// String username = auth.getName();

		boolean confirmationResult = false;
		try {
			CompletableFuture<Boolean> confirmationFuture = confirmationService.getUserConfirmation();
			confirmationResult = confirmationFuture.get();
		} catch (InterruptedException e) {
			System.out.println("unhandled exception handeled");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {

		}
		if (confirmationResult) {

			ApiResponse<String> placeOrder;
			try {
				placeOrder = cartService.placeOrder(username);
			} catch (FeignException.NotFound e) {
				ByteBuffer byteBuffer = null; // The ByteBuffer you want to convert to a String
				Charset charset = Charset.forName("UTF-8"); // Specify the character encoding (e.g., UTF-8)

				// Extract the response body from the exception
				byteBuffer = e.responseBody().get();
				// Decode the bytes from the ByteBuffer into a String
				String str = charset.decode(byteBuffer).toString();
				// Parse the JSON response body
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode = objectMapper.readTree(str);
				int intValue = jsonNode.get("status").intValue();
				HttpStatus stat = HttpStatus.valueOf(intValue);
				String message = jsonNode.get("message").asText();
				JsonNode dataNode = jsonNode.get("data");
				String data = objectMapper.writeValueAsString(dataNode);
				// Convert dataNode
				// to JSON string

				placeOrder = new ApiResponse(stat, message, data);
				// Retrieve the desired data from the JSON
				String jsonData = jsonNode.toString();
				System.err.println(jsonData);
				// Use the extracted JSON data as needed
				// ...
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(placeOrder);
			}

			return ResponseEntity.ok(placeOrder);
		} else {
			// String placeOrder = cartService.placeOrder(username);
			throw new OrderNotConfirmedException("Order not confirmed by User");
		}

	}

	@GetMapping("/addToCart/{bookId}/")
	@ApiOperation(value = "Add Book To Cart")
	// @CircuitBreaker(name = "AllServiceBreaker", fallbackMethod =
	// "serviceFallback")
	public ResponseEntity<?> addToCart(@PathVariable Long bookId, @RequestHeader("loggedInUser") String username)
			throws JsonMappingException, JsonProcessingException {
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// String username = auth.getName();
		ApiResponse<Cart> addToCart = null;

		try {
			addToCart = cartService.addToCart(bookId, username);
		}
		/*
		 * catch (Exception e) { System.err.println(e.getMessage()); throw new
		 * ResourceNotFoundException("cart-Service Or This BookID not available"); }
		 */
		catch (FeignException.NotFound e) {
			ByteBuffer byteBuffer = null; // The ByteBuffer you want to convert to a String
			Charset charset = Charset.forName("UTF-8"); // Specify the character encoding (e.g., UTF-8)

			// Extract the response body from the exception
			byteBuffer = e.responseBody().get();
			// Decode the bytes from the ByteBuffer into a String
			String str = charset.decode(byteBuffer).toString();
			// Parse the JSON response body
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(str);
			int intValue = jsonNode.get("status").intValue();
			HttpStatus stat = HttpStatus.valueOf(intValue);
			String message = jsonNode.get("message").asText();
			JsonNode dataNode = jsonNode.get("data");
			// String data = objectMapper.writeValueAsString(dataNode);
			// Convert dataNode
			// to JSON string
			Cart cart = null;
			addToCart = new ApiResponse(stat, message, cart);
			// Retrieve the desired data from the JSON
			String jsonData = jsonNode.toString();
			System.err.println(jsonData);
			// Use the extracted JSON data as needed
			// ...
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(addToCart);
		}

		System.out.println("add to cart" + username);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(addToCart);
	}

	@GetMapping("/deleteOrderFromCartById/{id}")
	@ApiOperation(value = "Deleted Order from cart by id")
	// @CircuitBreaker(name = "AllServiceBreaker", fallbackMethod =
	// "serviceFallback")
	public ResponseEntity<?> deleteOrderById(@PathVariable Long id, @RequestHeader("loggedInUser") String username) {
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// String username = auth.getName();
		boolean deletedOrderFromCart;

		try {
			deletedOrderFromCart = cartService.deletedOrderFromCart(id, username);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Cart-Service Or No any Order available with this id");
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(deletedOrderFromCart);
	}

	@GetMapping("/Confirm/{yes_no}")
	@ApiOperation(value = "Get User Confirmation To Place Order")
	// @CircuitBreaker(name = "AllServiceBreaker", fallbackMethod =
	// "serviceFallback")
	public ResponseEntity<?> confirmOrder(@PathVariable boolean yes_no) {
		boolean userConfirmation = confirmationService.getUserConfiramtion(yes_no);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userConfirmation);
	}

	public ResponseEntity<?> serviceFallback(RuntimeException e) {
		String service = "Service is down";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service);
	}

	public String orderServiceFallback(@RequestHeader("loggedInUser") String username, RuntimeException e) {
		String service = "Service is down";
		return service;
	}
}
