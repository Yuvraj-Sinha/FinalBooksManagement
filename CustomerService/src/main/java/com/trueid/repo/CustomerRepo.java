package com.trueid.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trueid.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, String> {
	public Customer findByUser(String username);
}
