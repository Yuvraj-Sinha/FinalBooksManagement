package com.trueid.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trueid.entity.User;

public interface UserRepo extends JpaRepository<User, String> {
	// public User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.username = ?1")
	public User findByUsername(String username);
}
