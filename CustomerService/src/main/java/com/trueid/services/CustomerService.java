package com.trueid.services;

import java.util.List;

import com.trueid.dto.CustomerDto;
import com.trueid.entity.Customer;

public interface CustomerService {

	public Customer saveCustomer(CustomerDto customerDto);

	public List<Customer> getCustomer();

	public Customer getCustomerById(String id);

	public CustomerDto getCustomerByName(String username);

}
