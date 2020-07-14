package com.sim.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.sim.cms.vo.PostMsgDTO;

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
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LikedService likedService;
	
	
	@GetMapping(value = "/getAll/{userId}")
		
	public ResponseEntity<Map<String, Object>> getAll(@PathVariable("userId") long userId,Pageable pageable){	
		PostMsg postMsg=null;
		PostMsgDTO postMsgDTO=null;	
		Pageable pageable1 = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
		Page<PostMsg> postPage= postMsgService.getAllPage(pageable1);		
		List<PostMsgDTO> postMsgDTOs=new ArrayList<>();
		Iterator<PostMsg> iterator= postPage.iterator();
		while (iterator.hasNext()) {
			postMsg =  iterator.next();
			postMsgDTO=convertObjToDTO(postMsg, new TypeReference<PostMsgDTO>(){});
			Optional<ImageModel> imageModelOp=imageService.findByUserId(postMsg.getUserId());
			if(imageModelOp.isPresent()) {				
				postMsgDTO.setPicByte(ImageCotroller.decompressBytes(imageModelOp.get().getPicByte()));
			}
			Optional<User> userOp=userService.findById(postMsg.getUserId());
			if(userOp.isPresent()) {
				postMsgDTO.setUsername(userOp.get().getUsername());
			}
			Optional<Liked> likedOp= likedService.findByPostMsgIdAndUserId(postMsg.getId(), userId);
			if(likedOp.isPresent()) {
				postMsgDTO.setLiked(likedOp.get().isLiked());
			}
			postMsgDTOs.add(postMsgDTO);
		}
		
		Map<String, Object> response = new HashMap<>();
	      response.put("content", postMsgDTOs);
	      response.put("currentPage", postPage.getNumber());
	      response.put("totalItems", postPage.getTotalElements());
	      response.put("totalPages", postPage.getTotalPages());
	      return new ResponseEntity<>(response, HttpStatus.OK);
	}
	 
	@GetMapping(value = "/{id}")
	public ApiResponse<PostMsg> getPostMsg(@PathVariable("id") long id) throws EmployeeIdNotFoundException{		
		Optional<PostMsg> postMsgOp= postMsgService.findById(id);
		if(!postMsgOp.isPresent()) 
			throw new EmployeeIdNotFoundException(ACTION.replace(ACTION_1, id+""));		
		return new ApiResponse<>(HttpStatus.OK.value(), "Get Posts Deatails", postMsgOp.get());
	}
	
	
	@PostMapping(value = "/add") 
	public ApiResponse<PostMsg> postMsg(@RequestBody PostMsgDTO postMsgDTO) {
		log.info("========="+postMsgDTO);
		PostMsg postMsg=null;
		try {
		postMsg=convertObjToE(postMsgDTO, new TypeReference<PostMsg>(){});
		postMsg=postMsgService.save(postMsg);
		}catch (Exception e) {
			log.info("==========="+e.toString());
			e.printStackTrace();
		}
		return new ApiResponse<>(HttpStatus.OK.value(), "Post Deatails are SuccessFully Added", postMsg);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ApiResponse<Void> deletePostMsg(@PathVariable("id") long id) {		
		postMsgService.delete(id);	
		return new ApiResponse<>(HttpStatus.OK.value(), "Post Message SuccessFully Deleted", null);
	}
	  
	public  PostMsg convertObjToE(Object o, TypeReference<PostMsg> ref) {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.convertValue(o, ref);
	}
	public  PostMsgDTO convertObjToDTO(Object o, TypeReference<PostMsgDTO> ref) {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.convertValue(o, ref);
	}
	
}
