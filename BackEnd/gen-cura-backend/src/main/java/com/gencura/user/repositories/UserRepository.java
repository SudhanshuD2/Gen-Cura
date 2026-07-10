package com.gencura.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gencura.user.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
