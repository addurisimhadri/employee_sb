package com.sim.cms.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sim.cms.entities.ImageModel;
import com.sim.cms.entities.PostMsg;
import com.sim.cms.service.ImageService;
import com.sim.cms.service.PostMsgService;
import com.sim.cms.service.UserService;
import com.sim.cms.vo.ApiResponse;
import com.sim.cms.vo.PostMsgDTO;

import lombok.extern.java.Log;

@CrossOrigin(origins = "*", allowedHeaders = "*",allowCredentials = "false")
@RestController
@RequestMapping(value = "/upload")
@Log
public class ImageCotroller {
	
	@Autowired
	ImageService imageService;
		
	@Autowired
	UserService userService;
	
	@Autowired
	PostMsgService postMsgService;
	
	
	@PostMapping("/user/{userId}")
	public ApiResponse<Void> uploadPhoto(@RequestParam("imageFile") MultipartFile file,@PathVariable("userId") long userId) {
		
		try {
			final Optional<ImageModel> retrievedImage = imageService.findByUserId(userId);
			if (retrievedImage.isPresent()) {
				log.info("Update Original Image Byte Size - " + file.getBytes().length);
				ImageModel img=retrievedImage.get();
				img.setPicByte(compressBytes(file.getBytes()));
				imageService.save(img);
			}else {
				log.info("New Original Image Byte Size - " + file.getBytes().length);
				ImageModel img = new ImageModel(userId,file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));
				imageService.save(img);
			}	
		}catch (Exception e) {
			log.info("==="+e.toString());
		}
		return new ApiResponse<>(HttpStatus.OK.value(),"Image successfully Uploaded.",null);
	}
	@GetMapping(path = { "/getImage/{userId}" })
    public byte[] getImage(HttpServletResponse response,@PathVariable("userId") long userId) throws Exception  {
		response.setContentType("image/jpeg");
		ImageModel img =null;
        final Optional<ImageModel> retrievedImage = imageService.findByUserId(userId);
        if(retrievedImage.isPresent()) {
        	log.info("Image Is exist...");
        	img = new ImageModel(userId,retrievedImage.get().getName(), retrievedImage.get().getType(),
                decompressBytes(retrievedImage.get().getPicByte()));
        	log.info("Image Is exist...");
        }		
        return img.getPicByte();
    }
	@GetMapping(path = { "/getImageM/{userId}" })
    public ImageModel getImageModel(HttpServletResponse response,@PathVariable("userId") long userId) throws Exception  {
		response.setContentType("image/jpeg");
		ImageModel img =null;
        final Optional<ImageModel> retrievedImage = imageService.findByUserId(userId);
        if(retrievedImage.isPresent()) {
        	log.info("Image Is exist...");
        	img = new ImageModel(userId,retrievedImage.get().getName(), retrievedImage.get().getType(),
                decompressBytes(retrievedImage.get().getPicByte()));
        	log.info("Image Is exist...");
        }		
        return img;
    }
	
	// compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        	
        }
        log.info("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        	log.info("Ex::"+ioe.getMessage());
        } catch (DataFormatException e) {        	
        	log.info("Ex::"+e.getMessage());
        }
        return outputStream.toByteArray();
    }
    public  PostMsg convertObjToXXX(Object o, TypeReference<PostMsg> ref) {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.convertValue(o, ref);
	}
	public  PostMsgDTO convertObjToX(Object o, TypeReference<PostMsgDTO> ref) {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.convertValue(o, ref);
	}
}
