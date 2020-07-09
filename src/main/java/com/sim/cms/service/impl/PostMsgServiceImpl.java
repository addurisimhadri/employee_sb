package com.sim.cms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sim.cms.entities.PostMsg;
import com.sim.cms.repo.PostMsgRepository;
import com.sim.cms.service.PostMsgService;

@Service
public class PostMsgServiceImpl implements PostMsgService {
	
	@Autowired
	PostMsgRepository postMsgRepository;
	
	@Override
	public Iterable<PostMsg> getAll(Pageable pageable) {
		return postMsgRepository.findAll(pageable);
	}

	@Override
	public Optional<PostMsg> findById(long id) {
		return postMsgRepository.findById(id);
	}

	@Override
	public PostMsg save(PostMsg postMsg) {
		return postMsgRepository.save(postMsg);
	}

	@Override
	public void delete(long id) {
		postMsgRepository.deleteById(id);
	}

}
