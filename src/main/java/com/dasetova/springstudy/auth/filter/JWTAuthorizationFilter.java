package com.dasetova.springstudy.auth.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.dasetova.springstudy.auth.SimpleGrantedAuthoritiesMixin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String header = request.getHeader("Authorization");

		if (!requiresAuthentication(header)) {
			chain.doFilter(request, response);
			return;
		}

		boolean tokenValid = true;
		Claims token = null;
		try {
			token = Jwts.parser().setSigningKey("A.Very.Secret.P@ssw0rd".getBytes())
					.parseClaimsJws(header.replace("Bearer ", "")).getBody();
		} catch (JwtException | IllegalArgumentException e) {
			e.printStackTrace();
			tokenValid = false;
		}
		
		UsernamePasswordAuthenticationToken auth = null;
		if(tokenValid) {
			String username = token.getSubject();
			Object roles = token.get("authorities");
			
			Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
					.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthoritiesMixin.class)
					.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
			
			auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
		}
		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(request, response);

	}

	protected boolean requiresAuthentication(String header) {
		if (header == null || !header.startsWith("Bearer ")) {

			return false;
		}
		return true;
	}

}
