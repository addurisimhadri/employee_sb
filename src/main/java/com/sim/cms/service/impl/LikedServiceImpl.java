package com.sim.cms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.cms.entities.Liked;
import com.sim.cms.repo.LikedRepository;
import com.sim.cms.service.LikedService;

@Service
public class LikedServiceImpl implements LikedService {
	
	@Autowired
	LikedRepository likedRepository;

	@Override
	public Liked save(Liked liked) {
		return likedRepository.save(liked);
	}

	@Override
	public List<Liked> getAll() {
		return likedRepository.findAll();
	}

	@Override
	public Optional<Liked> findByIdAndPostMsgId(long id, long postId) {
		return likedRepository.findByIdAndPostMsgId(id, postId);
	}

	@Override
	public Optional<Liked> findByPostMsgIdAndUserId(long postId, long userId) {
		return likedRepository.findByPostMsgIdAndUserId(postId, userId);
	}

}
