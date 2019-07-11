package com.stream.auth;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTToken {

	public static Key SecretKey = key();
	
	public static Key key() {
		byte[] bytes = {95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95,95};
		return Keys.hmacShaKeyFor(bytes);
	}
	
	public static String jws(String email, Key key) {
		
		return Jwts.builder()
				.setSubject(email)
				.claim(Claims.EXPIRATION, new Date(new Date().getTime()+30000))
				.signWith(key)
				.compact();
	}
	
	public static boolean verify(String jws, Key key) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(jws);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}
	
}
