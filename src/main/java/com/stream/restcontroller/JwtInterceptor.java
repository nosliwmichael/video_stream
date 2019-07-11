package com.stream.restcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.stream.auth.JWTToken;

public class JwtInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		//TODO request.authenticate(response)
		String uri = request.getRequestURI();
		String requestMethod = request.getMethod();
		if ("OPTIONS".equals(requestMethod)) {
			System.out.println(requestMethod + "=>" + uri);
			return true;
		}
		String token = request.getHeader("Authorization");
		if (token != null) {
			System.out.println(requestMethod + "=>" + uri + " | " + token);
			if (JWTToken.verify(token, JWTToken.SecretKey)) {
				return true;
			}
			response.setStatus(401);
		}
		System.out.println(requestMethod + "=>" + uri + " | Invalid token: " + token);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
