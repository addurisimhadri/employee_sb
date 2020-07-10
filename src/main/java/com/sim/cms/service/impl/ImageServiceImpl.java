package com.sim.cms.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.cms.entities.ImageModel;
import com.sim.cms.repo.ImageRepository;
import com.sim.cms.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	ImageRepository imageRepository;

	@Override
	public ImageModel save(ImageModel imageModel) {
		return imageRepository.save(imageModel);
	}

	@Override
	public Optional<ImageModel> findByUserId(long userId) {
		return imageRepository.findByUserId(userId);
	}

}
