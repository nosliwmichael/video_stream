package com.stream.restcontroller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stream.service.VideoService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(value="/api/video")
public class VideoController {

	@Autowired
	VideoService videoService;
	
	@GetMapping(value="/")
	public ResponseEntity<List<String>> getAll() {
		
		List<String> videoNames = videoService.getVideoNames();
		if (videoNames.size() > 0) {
			return new ResponseEntity<>(videoNames, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		
	}

	@GetMapping(value="/stream/{fileName}")
	public ResponseEntity<InputStreamResource> stream(
			@PathVariable String fileName, 
			@RequestHeader(value="Range", required=false) String range) throws FileNotFoundException {
		
		long rangeStart = Long.parseLong(range.replace("bytes=",  "").split("-")[0]);
		long rangeLength = videoService.fileSize(fileName);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "video/mp4");
		headers.set("Accept-Ranges", "bytes");
		headers.set("Cache-Control", "no-cache, no-store");
		headers.set("Connection", "keep-alive");
		headers.set("Content-Transfer-Encoding", "binary");
		headers.set("Content-Length", String.valueOf(rangeLength));
		headers.set("Content-Range", String.format("bytes %s-%s/%s", rangeStart, rangeStart, rangeLength));
		
		InputStreamResource inputStream = videoService.getVideo(fileName, range);
		
		if (inputStream != null) {
			return new ResponseEntity<>(inputStream, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		
	}
	
}