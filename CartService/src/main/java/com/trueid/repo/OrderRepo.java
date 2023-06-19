package com.trueid.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.trueid.entity.Order;

public interface OrderRepo extends JpaRepository<Order, String> {
	@Modifying
	@Query(value = "delete from Orders where cart_id= ?1", nativeQuery = true)
	public void deleteByCartId(String cartId);

	@Query(value = "delete from Orders where id= ?1", nativeQuery = true)
	public boolean deleteByBookId(Long bookId);
}
