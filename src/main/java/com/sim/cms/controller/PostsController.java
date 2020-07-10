package com.sim.cms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.sim.cms.entities.PostMsg;
import com.sim.cms.exception.EmployeeIdNotFoundException;
import com.sim.cms.service.PostMsgService;
import com.sim.cms.vo.ApiResponse;
import com.sim.cms.vo.EmployeeDTO;

import lombok.extern.java.Log;

@CrossOrigin(origins = "*", allowedHeaders = "*",allowCredentials = "false")
@RestController
@RequestMapping(value = "/post")
@Log
public class PostsController {
	private static final String ACTION = "PostMsg id is ##ID## Not exist.";
	private static final String ACTION_1 = "##ID##";
	
	@Autowired
	PostMsgService postMsgService;
	
	
	@GetMapping(value = "/getAll")
		
	public ApiResponse<Iterable<PostMsg>> getAll(Pageable pageable){		
		Iterable<PostMsg> posIterable= postMsgService.getAll(pageable);
		return new ApiResponse<>(HttpStatus.OK.value(), "Get All Post Deatails", posIterable);
	}
	
	@GetMapping(value = "/{id}")
	public ApiResponse<PostMsg> getPostMsg(@PathVariable("id") long id) throws EmployeeIdNotFoundException{		
		Optional<PostMsg> postMsgOp= postMsgService.findById(id);
		if(!postMsgOp.isPresent()) 
			throw new EmployeeIdNotFoundException(ACTION.replace(ACTION_1, id+""));		
		return new ApiResponse<>(HttpStatus.OK.value(), "Get Posts Deatails", postMsgOp.get());
	}
	
	
	@PostMapping(value = "/add") 
	public ApiResponse<PostMsg> postMsg(@RequestBody PostMsg postMsg) {
		
		postMsgService.save(postMsg);
		return new ApiResponse<>(HttpStatus.OK.value(), "Post Deatails are SuccessFully Added", postMsg);
	}
	  
	 
	
}
