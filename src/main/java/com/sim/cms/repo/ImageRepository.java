package com.sim.cms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sim.cms.entities.ImageModel;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Long> {
	
	Optional<ImageModel> findByUserId(long userId);

}
