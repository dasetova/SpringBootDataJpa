package com.dasetova.springstudy.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dasetova.springstudy.models.entity.Customer;

@Service
public class UploadFileServiceImpl implements IUploadFileService {
	
	// Podría tomar un valor de application.properties con @Value
	private final static String UPLOADS_FOLDER = "uploads";
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Resource load(String filename) throws MalformedURLException {
		// TODO Auto-generated method stub
		Path resourcesPath = this.getPath(filename);
		log.info("PathPhoto: " + resourcesPath);
		
		Resource resource = new UrlResource(resourcesPath.toUri());
		if(!resource.exists() || !resource.isReadable()) {
			throw new RuntimeException("Error: Can't charge image " + resourcesPath.toString());
		}
	
		return resource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename(); 
		Path resourcesPath = this.getPath(uniqueFilename);
		Path fullPath = resourcesPath.toAbsolutePath();
		
		log.info("rootPath: " + resourcesPath);
		log.info("rootPath: " + fullPath);
		
		Files.copy(file.getInputStream(), fullPath);
		
		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = this.getPath(filename);
		File file = rootPath.toFile();
		
		if(file.exists() && file.canRead()) {
			return file.delete();
		}
		return false;
	}
	
	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}
}
