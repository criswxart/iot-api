package com.tld.configuration.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;




@Component
public class JwtUtils {
	
	@Value("${secret.private.key.jwt}")
	private String PRIVATE_KEY;
	
	@Value("${secret.private.user.jwt}")
	private String USER_GENERATOR;
	
	private final static int EXPIRATION_OFFSET = 1_800_000;
	
	private final static int NOT_BEFORE_OFFSET = 10_000;

	public String createToken(Authentication authentication) {
        final String username = authentication.getName();

        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setIssuer(USER_GENERATOR)
                .setSubject(username)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_OFFSET))
                .setNotBefore(new Date(System.currentTimeMillis() + NOT_BEFORE_OFFSET))
                .setId(UUID.randomUUID().toString())
                .signWith(getSigninKey())
                .compact();
    }
	public boolean validateToken(String token) {
        try {
            parseJwt(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	 private Jws<Claims> parseJwt(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSigninKey())
	                .build()
	                .parseClaimsJws(token);
	    }
	 public String getUsernameFromToken(String token) {
	        return parseJwt(token).getBody().getSubject();
	    }
	 public Authentication getAuthentication(String token, UserDetails userDetails) {
	        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	    }
	 private Key getSigninKey() {
	        return new SecretKeySpec(Base64.getDecoder().decode(PRIVATE_KEY), SignatureAlgorithm.HS256.getJcaName());
	    }
	
}
