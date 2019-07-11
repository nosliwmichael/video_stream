package com.stream.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;

public interface VideoService {

	public long fileSize(String fileName);
	public List<String> getVideoNames();
	public InputStreamResource getVideo(String fileName, String range) throws FileNotFoundException;
	
}
