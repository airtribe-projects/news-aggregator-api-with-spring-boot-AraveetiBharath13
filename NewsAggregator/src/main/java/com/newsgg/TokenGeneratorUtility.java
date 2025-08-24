package com.newsgg;

import java.util.Date;

import com.newsgg.entity.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

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

    public static Claims validateSignedToken(String authorizationHeader) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey("KdcgDPiU3WDtxMeRjR80XCwwQDU3lbVTHUn8EtqE88ilRP/lNhrdpSOA4YS7j1Tr")
                    .build()
                    .parseClaimsJws(authorizationHeader)
                    .getBody();
            System.out.print("Claims: " + body);
            return body;
        } catch (SignatureException exception) {
            System.err.println("Invalid JWT signature: " + exception.getMessage());
            return null;
        } catch (io.jsonwebtoken.ExpiredJwtException exception) {
            System.err.println("JWT token is expired: " + exception.getMessage());
            return null;
        } catch (io.jsonwebtoken.MalformedJwtException exception) {
            System.err.println("Invalid JWT token: " + exception.getMessage());
            return null;
        } catch (io.jsonwebtoken.UnsupportedJwtException exception) {
            System.err.println("JWT token is unsupported: " + exception.getMessage());
            return null;
        } catch (IllegalArgumentException exception) {
            System.err.println("JWT claims string is empty: " + exception.getMessage());
            return null;
        }

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
