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

import com.dasetova.springstudy.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTServiceImpl implements JWTService {

	@Override
	public String create(Authentication auth) throws JsonProcessingException {
		// TODO Auto-generated method stub
		String username = ((User) auth.getPrincipal()).getUsername();

		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

		String token = Jwts.builder().setClaims(claims).setSubject(username)// authResult.getName() //Usuario
				.signWith(SignatureAlgorithm.HS512, "A.Very.Secret.P@ssw0rd".getBytes()).setIssuedAt(new Date()) // Fecha
																													// de
																													// creacion
				.setExpiration(new Date(System.currentTimeMillis() + 3600000L)) // Fecha de expiracion
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
		return Jwts.parser().setSigningKey("A.Very.Secret.P@ssw0rd".getBytes())
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
		if(token!=null && token.startsWith("Bearer")) {
			return token.replace("Bearer ", "");
		}
		return null;
	}

}
