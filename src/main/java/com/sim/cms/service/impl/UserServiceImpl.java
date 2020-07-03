package com.sim.cms.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.cms.entities.User;
import com.sim.cms.repo.UserRepository;
import com.sim.cms.service.UserService;
import com.sim.cms.vo.LoginRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Override
	public Optional<User> validateUser(LoginRequest login) {
		return userRepository.findByUsernameAndPassword(login.getUsername(), login.getPassword());
	}
	

}
