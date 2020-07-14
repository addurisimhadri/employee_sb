package com.sim.cms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sim.cms.entities.ImageModel;
import com.sim.cms.entities.Liked;
import com.sim.cms.entities.PostMsg;
import com.sim.cms.entities.User;
import com.sim.cms.exception.EmployeeIdNotFoundException;
import com.sim.cms.service.ImageService;
import com.sim.cms.service.LikedService;
import com.sim.cms.service.PostMsgService;
import com.sim.cms.service.UserService;
import com.sim.cms.vo.ApiResponse;

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
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	UserService userService;
		
	@PostMapping(value = "/postmsg/{postId}/{userId}/liked")
	public ApiResponse<PostMsg> addLiked(@PathVariable("postId") long postId,@PathVariable("userId") long userId)throws EmployeeIdNotFoundException  {
		Liked liked=new Liked();
		liked.setLiked(true);
		PostMsg postMsg=null;
		Optional<PostMsg> postMsgOp=postMsgService.findById(postId);
		String msg="";
		if(postMsgOp.isPresent()) {			
			postMsg=postMsgOp.get();
			Optional<Liked> likedOp=likedService.findByPostMsgIdAndUserId(postId, userId);
			if(!likedOp.isPresent() ) {
				Optional<ImageModel> imageModelOp=imageService.findByUserId(postMsg.getUserId());
				if(imageModelOp.isPresent()) {				
					postMsg.setPicByte(ImageCotroller.decompressBytes(imageModelOp.get().getPicByte()));
				}
				Optional<User> userOp=userService.findById(postMsg.getUserId());
				if(userOp.isPresent()) {
					postMsg.setUsername(userOp.get().getUsername());
				}
				liked.setUserId(userId);
				liked.setPostMsg(postMsgOp.get());
				likedService.save(liked);
				postMsg.setLikeCount(postMsg.getLikeCount()+1);
				postMsgService.save(postMsg);
				postMsg.setLiked(true);	
				msg="You liked this comment.";
			}else {
				Optional<ImageModel> imageModelOp=imageService.findByUserId(postMsg.getUserId());
				if(imageModelOp.isPresent()) {				
					postMsg.setPicByte(ImageCotroller.decompressBytes(imageModelOp.get().getPicByte()));
				}
				Optional<User> userOp=userService.findById(postMsg.getUserId());
				if(userOp.isPresent()) {
					postMsg.setUsername(userOp.get().getUsername());
				}
				likedService.delete(likedOp.get().getId());
				postMsg.setLikeCount(postMsg.getLikeCount()-1);
				postMsgService.save(postMsg);
				postMsg.setLiked(false);
				msg="You disliked this comment.";
			}
		}else {
			throw new EmployeeIdNotFoundException(ACTION.replace(ACTION_1, postId+""));
		}		
		return new ApiResponse<>(HttpStatus.OK.value(), msg, postMsg);
	}
	

}
