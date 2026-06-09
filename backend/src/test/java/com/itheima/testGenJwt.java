package com.itheima;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class testGenJwt {

//	@Test
//	public void testGenJwt() {
//		
//		Map<String, Object> claims = new HashMap<String, Object>();
//		claims.put("id", 10);
//		claims.put("username", "itheima");
//		
//		String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "aXRjYXN0")
//				.addClaims(claims)
//				.setExpiration(new Date(System.currentTimeMillis() + 12*3600*1000))
//				.compact();
//		
//		System.out.println(jwt);
//	}
	
	@Test
	public void testParseJwt() {
		
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTAsInVzZXJuYW1lIjoiaXRoZWltYSIsImV4cCI6MTc3ODEyNzkyNH0.87V62-4Wnp5X2jA_L2XDMkcNj8ZrvrWkhFFmNL2HsHU";
		Claims claims = Jwts.parser().setSigningKey("aXRjYXN0").parseClaimsJws(token).getBody();
		System.out.println(claims);
	}
}
