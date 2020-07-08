package com.sim.cms.service;

import java.util.Optional;

import com.sim.cms.entities.User;
import com.sim.cms.vo.LoginRequest;

public interface UserService {	
	Optional<User> validateUser(LoginRequest login);
	Optional<User> findById(long id);

}
