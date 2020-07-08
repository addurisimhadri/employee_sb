package com.sim.cms.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sim.cms.entities.Liked;

@Repository
public interface LikedRepository extends JpaRepository<Liked, Long> {
	
	Optional<Liked> findByIdAndPostMsgId(long id,long postId);
	Optional<Liked> findByPostMsgIdAndUserId(long postId,long userId);
	List<Liked> findByPostMsgId(long postId);
}
