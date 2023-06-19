package com.trueid.rest;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trueid.custom.response.ApiResponse;
import com.trueid.dto.BookDto;
import com.trueid.entity.Cart;
import com.trueid.entity.Order;
import com.trueid.exception.ResourceNotFoundException;
import com.trueid.external.services.BookStore;
import com.trueid.repo.CartRepo;
import com.trueid.repo.OrderRepo;
import com.trueid.services.CartService;

import feign.FeignException;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	BookStore bookStore;
	@Autowired
	CartRepo cartRepo;
	@Autowired
	CartService cartService;
	@Autowired
	OrderRepo orderRepo;

	@GetMapping("/addBook/{id}/username/{username}")

	public ResponseEntity<ApiResponse<Cart>> addToCart(@PathVariable Long id, @PathVariable String username) {
		ApiResponse<Cart> response = null;
		BookDto book = null;

		ApiResponse<BookDto> resp = null;

		try {
			resp = bookStore.getBook(id);
		} catch (FeignException.NotFound e) {
			System.out.println("Message" + e.getMessage());
			throw new ResourceNotFoundException("Book Not Found BY This ID :-" + id);
		}
		book = resp.getData();

		List<Order> orders;
		Cart cart;
		Cart save = null;
		try {
			cart = cartRepo.findByUsername(username);

			orders = cart.getOrder();
			Order order = new Order();
			order.setId(book.getId());
			order.setTitle(book.getTitle());
			order.setCategory(book.getCategory());
			order.setAuthor(book.getAuthor());
			orders.add(order);
			order.setCart(cart);
		} catch (Exception e) {
			cart = new Cart();
			cart.setUsername(username);
			orders = new ArrayList<>();
			Order order = new Order();
			order.setId(book.getId());
			order.setTitle(book.getTitle());
			order.setCategory(book.getCategory());
			order.setAuthor(book.getAuthor());
			orders.add(order);
			order.setCart(cart);
		}

		try {
			cart.setOrder(orders);

			// cart.setUsername("yuvraj");

			save = cartRepo.save(cart);

			response = new ApiResponse<>(HttpStatus.OK, "Book Added To Cart SuccessFully", save);

		} catch (

		Exception e) {
			System.out.println("In error" + e.getMessage());
		}

		System.out.println(book);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

	// getCart/{username}
	@GetMapping("getCart/{username}")
	public Cart getCart(@PathVariable String username) {
		Cart cart = cartRepo.findByUsername(username);
		System.out.println(cart);
		return cart;
	}

	@GetMapping("/placeOrder/{username}")

	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<ApiResponse<String>> placeOrder(@PathVariable String username) {
		System.out.println("In place Order");
		ApiResponse<String> orderPlace = cartService.orderPlace(username);
		if (orderPlace == null) {
			throw new ResourceNotFoundException(" No  Any Order  Available In your Cart");
		} else if (orderPlace.getMessage().equals("Order Placed Successfully")) {

			// ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK, "Order
			// Placed SuccessFully", );
			return ResponseEntity.ok(orderPlace);
		} else {
			throw new ResourceNotFoundException(orderPlace.getMessage());
		}

	}

	@GetMapping("/deleteOrder/{id}/username/{username}")
	public ResponseEntity<?> deleteOrderFromCart(@PathVariable Long id, @PathVariable String username) {
		Cart cart = cartRepo.findByUsername(username);
		boolean bookId = false;
		List<Order> order = cart.getOrder();
		for (Order ord : order) {
			if (ord.getId().equals(id)) {
				try {

					bookId = orderRepo.deleteByBookId(id);

				} catch (Exception e) {
					bookId = true;
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookId);
				}
			}
		}

		/*
		 * try { bookId = orderRepo.deleteByBookId(id); } catch (AopInvocationException
		 * e) { e.getStackTrace(); throw new RuntimeException(); } catch (Exception e) {
		 * e.getStackTrace(); return
		 * ResponseEntity.status(HttpStatus.ACCEPTED).body(bookId); }
		 */
		if (bookId == false) {

			throw new RuntimeException("No any order available in cart with this id");
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookId);
	}

}
