package com.gencura.user.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gencura.user.entities.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	
	public boolean existsByRole(String role);
	
	public Optional<UserRole> findByRoleAndActiveTrue(String role);
	
	List<UserRole> findAllByActiveTrue();
	
	Optional<UserRole> findByRole(String role);

}
