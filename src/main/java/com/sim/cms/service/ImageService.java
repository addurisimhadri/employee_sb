package com.sim.cms.service;

import java.util.Optional;

import com.sim.cms.entities.ImageModel;

public interface ImageService {
	
	ImageModel save(ImageModel imageModel);
	Optional<ImageModel> findByUserId(long userId);

}
