package com.trueid.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trueid.custom.response.ApiResponse;
import com.trueid.dto.CustomerDto;
import com.trueid.entity.Customer;
import com.trueid.exception.UserAlreadyExist;
import com.trueid.services.CustomerService;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Customer Controller", description = "Customer REST Endpoints.")
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@PostMapping("/addCustomer")
	@ApiOperation(value = "Add New Customer")
	public ResponseEntity<?> saveCustomer(@RequestBody CustomerDto customer) {

		Customer customer2 = customerService.saveCustomer(customer);
		if (customer2 == null) {
			throw new UserAlreadyExist("User Already exist with this userId/userName");
		}
		ApiResponse<Customer> response = new ApiResponse<>(HttpStatus.OK, "Users added successfully", customer2);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	@ApiOperation(value = "get all customers")
	public List<Customer> getCustomer(HttpServletRequest req, @RequestHeader("loggedInUser") String username) {

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				System.err.println(auth.getName() + "" + auth.getDetails() + "" + auth.getPrincipal());

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return customerService.getCustomer();
	}

	@GetMapping("/getAllDetails")
	@ApiOperation(value = "get Customer whole details")
	@CircuitBreaker(name = "AllServiceBreaker", fallbackMethod = "allServiceFallback")
	public ResponseEntity<?> getCustomerById(@RequestHeader("loggedInUser") String username) {
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// String username = auth.getName();
		System.out.println(username);
		CustomerDto customer = customerService.getCustomerByName(username);
		String custId = customer.getCustId();

		Customer customerById = customerService.getCustomerById(custId);

		return ResponseEntity.status(HttpStatus.OK).body(customerById);
	}

	@GetMapping("/getUserDetails")
	@ApiOperation(value = "get only customer details")
	public CustomerDto getCustomerByUserName(@RequestHeader("loggedInUser") String username) {

		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// String username = auth.getName();
		System.out.println(username);
		return customerService.getCustomerByName(username);
	}

	public ResponseEntity<?> allServiceFallback(@RequestHeader("loggedInUser") String username, FeignException e) {

		// CustomerDto customer = customerService.getCustomerByName(username);
		String service = "Service is down";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service);
	}

}
