package com.sim.cms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.cms.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsernameAndPassword(String username,String password);
	User findByUsername(String username);
	
}
