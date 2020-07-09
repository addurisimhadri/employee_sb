package com.sim.cms.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.sim.cms.entities.PostMsg;

public interface PostMsgService {
	
	Iterable<PostMsg> getAll(Pageable pageable);
	Optional<PostMsg> findById(long id);
	PostMsg save(PostMsg postMsg);
	void delete(long id);

}
