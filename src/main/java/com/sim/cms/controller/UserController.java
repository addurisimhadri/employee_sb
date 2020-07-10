package com.sim.cms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sim.cms.entities.User;
import com.sim.cms.exception.EmployeeIdNotFoundException;
import com.sim.cms.service.UserService;
import com.sim.cms.vo.ApiResponse;

@CrossOrigin(origins = "*", allowedHeaders = "*",allowCredentials = "false")
@RestController
@RequestMapping(value = "/user")
public class UserController {
	private static final String ACTION = "User id is ##ID## Not exist.";
	private static final String ACTION_1 = "##ID##";
	@Autowired
	UserService userService;
	
	@GetMapping(value = "/{userId}")
	public ApiResponse<User> getUser(@PathVariable("userId") long id) throws  EmployeeIdNotFoundException{		
		Optional<User> user=userService.findById(id);
		if(!user.isPresent()) 
			throw new EmployeeIdNotFoundException(ACTION.replace(ACTION_1, id+""));	
		return new ApiResponse<>(HttpStatus.OK.value(),"Image successfully loaded.",user.get());
		
	}

}
