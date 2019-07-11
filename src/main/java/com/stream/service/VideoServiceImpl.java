package com.stream.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {

	private String directory = "D:\\Stream\\";
	private String extension = ".mp4";
	
	public long fileSize(String fileName) {
		long fileSize = new File(buildFilePath(fileName)).length();
		return fileSize;
	}
	
	public List<String> getVideoNames() {
		List<String> results = new ArrayList<>();
		File folder = new File(directory);
		for (File file : folder.listFiles()) {
			if (file.isFile()) {
				results.add(file.getName());
			}
		}
		return results;
	}
	
	public InputStreamResource getVideo(String fileName, String range) throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(buildFilePath(fileName));
		return new InputStreamResource(inputStream);
	}
	
	private String buildFilePath(String fileName) {
		return directory + fileName + extension;
	}
	
}
