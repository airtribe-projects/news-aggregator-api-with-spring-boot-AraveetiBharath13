package com.newsgg;

import java.io.IOException;
import java.util.List;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
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

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return; // stop further processing
        }

      //  String token = authHeader.substring(7); // remove "Bearer "
        Claims fetchedClaims = TokenGeneratorUtility.validateSignedToken(authHeader);
        if(fetchedClaims == null)  {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid user token");
            return;
        }

        String username = fetchedClaims.getSubject();
        String role = fetchedClaims.get("role", String.class);
        List<SimpleGrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(role));
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null, authorityList);

        SecurityContextHolder.getContext().setAuthentication(authentication);


        filterChain.doFilter(request, response);
	}
	
	 private static final List<String> EXCLUDED_URLS = List.of(
             "/api/login",
             "/api/register",
             "/h2-console",
             "/h2-console/**"
		    );

	 @Override
	 protected boolean shouldNotFilter(HttpServletRequest request) {
         String path = request.getRequestURI();
         return EXCLUDED_URLS.stream().anyMatch(path::startsWith);
	 }
}
