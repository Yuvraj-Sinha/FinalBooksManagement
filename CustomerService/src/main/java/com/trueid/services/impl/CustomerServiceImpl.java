package com.trueid.services.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.trueid.dto.CustomerDto;
import com.trueid.entity.Address;
import com.trueid.entity.Customer;
import com.trueid.entity.User;
import com.trueid.external.services.CartService;
import com.trueid.external.services.PlaceOrder;
import com.trueid.repo.CustomerRepo;
import com.trueid.repo.UserRepo;
import com.trueid.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepo customerRepo;
	/*
	 * @Autowired private Address address;
	 */
	@Autowired
	private UserRepo userRepo;
	@Autowired
	CartService cartService;
	@Autowired
	PlaceOrder placeOrder;
	private final ModelMapper modelMapper;

	@Autowired
	public CustomerServiceImpl(ModelMapper modelMapper) {

		this.modelMapper = modelMapper;
	}

	@Override
	public Customer saveCustomer(CustomerDto customerDto) {
		Customer customer = null;

		try {
			User username = userRepo.findByUsername(customerDto.getUser().getUsername());
			if (username == null) {
				System.err.println(username + "founded user with this username");
				throw new RuntimeException();
			}
			return null;
		} catch (Exception e1) {
			customer = modelMapper.map(customerDto, Customer.class);
			Address address2 = customer.getAddress();
			User user = customer.getUser();
			address2.setCustomer(customer);
			user.setCustomer(customer);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
		}

		return customerRepo.save(customer);

	}

	@Override
	public Customer getCustomerById(String id) {

		Customer customer = customerRepo.findById(id).orElseThrow();
		System.out.println(customer.getUser().getUsername());
		System.err.println(cartService.getCart(customer.getUser().getUsername()));

		customer.setCart(cartService.getCart(customer.getUser().getUsername()));
		System.out.println(placeOrder.getOrder(customer.getUser().getUsername()));
		customer.setOrderItem(placeOrder.getOrder(customer.getUser().getUsername()));
		return customer;
	}

	@Override
	public CustomerDto getCustomerByName(String username) {
		User user = userRepo.findByUsername(username);
		Customer customer = user.getCustomer();
		CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
		return customerDto;
	}

	@Override
	public List<Customer> getCustomer() {

		return customerRepo.findAll();
	}

}
