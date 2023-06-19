package com.trueid.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trueid.entity.OrderItem;

public interface OrderedItemRepo extends JpaRepository<OrderItem, String> {
	public List<OrderItem> findByUsername(String username);
}
