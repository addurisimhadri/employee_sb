package com.sim.cms.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sim.cms.entities.PostMsg;

@Repository
public interface PostMsgRepository extends JpaRepository<PostMsg, Long> {
	Page<PostMsg> findByOrderByCreatedAtDesc(Pageable pageable);

}
