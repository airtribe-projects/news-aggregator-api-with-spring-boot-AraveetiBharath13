package com.newsgg;

import java.util.Date;

import com.newsgg.entity.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class TokenGeneratorUtility  {
	
	public static String generateToke(String userName, String role) {
		
		return Jwts.builder()
                .setSubject(userName) // or user.getEmail(), etc.
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256,"KdcgDPiU3WDtxMeRjR80XCwwQDU3lbVTHUn8EtqE88ilRP/lNhrdpSOA4YS7j1Tr")
                .compact();
		
	}

	public static boolean validateToken(String authHeader) {
		try {
		Claims body = Jwts.parser().setSigningKey("KdcgDPiU3WDtxMeRjR80XCwwQDU3lbVTHUn8EtqE88ilRP/lNhrdpSOA4YS7j1Tr")
						  .build()
						  .parseClaimsJws(authHeader)
						  .getBody();
		}catch(Exception ex) {
			return false;
		}
		return true;
	}

}
