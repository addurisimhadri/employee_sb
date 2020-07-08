package com.sim.cms.service;

import java.util.List;
import java.util.Optional;

import com.sim.cms.entities.Liked;

public interface LikedService {
	
	Liked save(Liked liked);
	List<Liked> getAll();
	Optional<Liked> findByIdAndPostMsgId(long id, long postId);
	Optional<Liked> findByPostMsgIdAndUserId(long postId,long userId);
	
}
