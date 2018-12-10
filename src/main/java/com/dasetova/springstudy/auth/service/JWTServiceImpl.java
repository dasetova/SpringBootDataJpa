package com.dasetova.springstudy.auth.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.dasetova.springstudy.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTServiceImpl implements JWTService {
	
	public static final String SECRET=Base64Utils.encodeToString("A.Very.Secret.P@ssw0rd".getBytes());
	public static final long EXPIRATION_DATE = 3600000L;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	@Override
	public String create(Authentication auth) throws JsonProcessingException {
		// TODO Auto-generated method stub
		String username = ((User) auth.getPrincipal()).getUsername();

		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

		String token = Jwts.builder().setClaims(claims).setSubject(username)// authResult.getName() //Usuario
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).setIssuedAt(new Date()) // Fecha
																													// de
																													// creacion
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE)) // Fecha de expiracion
				.compact();
		return token;
	}

	@Override
	public boolean validate(String token) {
		try {
			getClaims(token);
		} catch (JwtException | IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Claims getClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser().setSigningKey(SECRET.getBytes())
				.parseClaimsJws(resolve(token)).getBody();
	}

	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = getClaims(token).get("authorities");
		
		return Arrays.asList(new ObjectMapper()
				.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
				.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
	}

	@Override
	public String resolve(String token) {
		if(token!=null && token.startsWith(TOKEN_PREFIX)) {
			return token.replace(TOKEN_PREFIX, "");
		}
		return null;
	}

}
