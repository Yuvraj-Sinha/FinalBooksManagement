package com.trueid.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trueid.entity.Cart;

public interface CartRepo extends JpaRepository<Cart, String> {
	public Cart findByUsername(String username);

	@Query(value = "delete from cart where username= ?1", nativeQuery = true)
	public boolean deleteByUsername(String username);
}
