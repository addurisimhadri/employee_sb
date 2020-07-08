package com.sim.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sim.cms.entities.PostMsg;
import com.sim.cms.service.PostMsgService;

@RestController
@RequestMapping(value = "/post")
public class PostsController {
	
	@Autowired
	PostMsgService postMsgService;
	
	
	@GetMapping(value = "/getAll")
		
	public List<PostMsg> getAll(){		
		return postMsgService.getAll();
	}
	
	@GetMapping(value = "/{id}")
	public PostMsg getPostMsg(@PathVariable("id") long id) {		
		return postMsgService.findById(id).get();
	}
	
	
	@PostMapping(value = "/add") 
	public PostMsg postMsg(@RequestParam("postmsg") String postmsg, @RequestParam("userId") long userId) {
		PostMsg postMsg1=new PostMsg();
		postMsg1.setMsg(postmsg);
		postMsg1.setUserId(userId);	
		return postMsgService.save(postMsg1);
	}
	  
	 
	
}
