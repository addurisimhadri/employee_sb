package com.sim.cms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sim.cms.entities.User;
import com.sim.cms.service.UserService;
import com.sim.cms.vo.LoginRequest;
import com.sim.cms.vo.LoginResponse;

@CrossOrigin(origins = "*", allowedHeaders = "*",allowCredentials = "false")
@RestController
@RequestMapping(value = "/api")
public class LoginController {
	
	@Autowired
	UserService userService;
	
	
	@PostMapping(path = { "/login" })
	public ResponseEntity<LoginResponse> create(@RequestBody LoginRequest login) {
		Optional<User> user=userService.validateUser(login);
		if(user.isPresent()) {
			return  new ResponseEntity<>(new LoginResponse(user.get().getUsername()),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new LoginResponse(""),HttpStatus.OK);
		}
		
	}

}
