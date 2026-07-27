package com.gencura.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gencura.user.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public boolean existsByEmail(String email);
	
	public Optional<User> findByEmailAndActiveTrue(String email);
	
	public boolean existsByMobile(String mobile);
	
	public Optional<User> findByMobileAndActiveTrue(String mobile);
}
