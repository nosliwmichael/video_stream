package com.stream.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stream.auth.JWTToken;
import com.stream.model.User;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(value="/api/user")
public class UserController {
		
	@PostMapping(value="/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
		if ("michael".equals(user.getEmail()) && "password".equals(user.getPassword())) {
			String token = JWTToken.jws(user.getEmail(), JWTToken.SecretKey);
			Map<String, Object> response = new HashMap<>();
			response.put("user", user);
			response.put("token", token);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping(value="/isValid")
	public ResponseEntity<Boolean> isValid() {
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
}
