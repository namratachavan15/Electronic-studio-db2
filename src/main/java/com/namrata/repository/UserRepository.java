package com.namrata.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.namrata.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findUserByEmail(String email);
	User findByEmail(String email);
	
}
