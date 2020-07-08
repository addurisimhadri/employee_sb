package com.sim.cms.service;

import java.util.List;
import java.util.Optional;

import com.sim.cms.entities.PostMsg;

public interface PostMsgService {
	
	List<PostMsg> getAll();
	Optional<PostMsg> findById(long id);
	PostMsg save(PostMsg postMsg);
	void delete(long id);

}
