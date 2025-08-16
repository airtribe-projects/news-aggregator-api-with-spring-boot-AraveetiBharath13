package com.newsgg;

import java.io.IOException;
import java.util.List;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("AuthorizationHeader");
		
		if(authHeader == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		if(!TokenGeneratorUtility.validateToken(authHeader)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		filterChain.doFilter(request, response);
	}
	
	 private static final List<String> EXCLUDED_URLS = List.of(
		        "/api/login",
		        "/api/register",
		        "/h2-console"
		    );

	 @Override
	 protected boolean shouldNotFilter(HttpServletRequest request) {
		 if(request.)
		return false;		
	 }

		  

}
