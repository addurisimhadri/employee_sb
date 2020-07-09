package com.sim.cms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sim.cms.entities.Liked;
import com.sim.cms.entities.PostMsg;
import com.sim.cms.exception.EmployeeIdNotFoundException;
import com.sim.cms.service.LikedService;
import com.sim.cms.service.PostMsgService;

import lombok.extern.java.Log;

@CrossOrigin(origins = "*", allowedHeaders = "*",allowCredentials = "false")
@RestController
@Log
public class LikedController {
	private static final String ACTION = "Liked id is ##ID## Not exist.";
	private static final String ACTION_1 = "##ID##";
	
	@Autowired
	LikedService likedService;
	
	@Autowired
	PostMsgService postMsgService;
		
	@PostMapping(value = "/postmsg/{postId}/{userId}/liked")
	public Liked addLiked(@PathVariable("postId") long postId,@PathVariable("userId") long userId)throws EmployeeIdNotFoundException  {
		Liked liked=new Liked();
		liked.setLiked(true);
		Optional<PostMsg> postMsgOp=postMsgService.findById(postId);
		if(postMsgOp.isPresent()) {
			PostMsg postMsg=postMsgOp.get();
			Optional<Liked> likedOp=likedService.findByPostMsgIdAndUserId(postId, userId);
			if(!likedOp.isPresent() ) {
				liked.setUserId(postMsgOp.get().getUserId());
				liked.setPostMsg(postMsgOp.get());
				likedService.save(liked);
				postMsg.setLikeCount(postMsg.getLikeCount()+1);
				postMsgService.save(postMsg);
				
			}else {
				log.info("You are alredy liked.............");
			}
		}else {
			throw new EmployeeIdNotFoundException(ACTION.replace(ACTION_1, postId+""));
		}		
		return liked;
	}
	

}
