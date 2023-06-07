package com.example.demo.services.implementations;

import com.example.demo.services.JWTTokenProviderService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class JWTTokenProviderServiceImpl implements JWTTokenProviderService {

	@Override
	public String generateToken(String username) {
		String secretKey = "yourSecretKey";
	        Date now = new Date();
	        Date expiryDate = new Date(now.getTime() + 86400000);

	        return Jwts.builder()
	                .setSubject(username)
	                .setIssuedAt(now)
	                .setExpiration(expiryDate)
	                .signWith(SignatureAlgorithm.HS512, secretKey)
	                .compact();
	    }
	

}
