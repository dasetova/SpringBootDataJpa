package com.dasetova.springstudy.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	
	
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
	}



	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if(username != null && password != null) {
			logger.info("Username: " + username);
			logger.info("Password: " + password);
		}else {
			com.dasetova.springstudy.models.entity.User user;
			try {
				user = new ObjectMapper()
						.readValue(request.getInputStream(), com.dasetova.springstudy.models.entity.User.class);
				username = user.getUsername();
				password = user.getPassword();
				
				logger.info("Username raw: " + username);
				logger.info("Password raw: " + password);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		username = username.trim();
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		return this.authenticationManager.authenticate(authToken);
	}



	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//Cuando es exitoso el login llegara a este metodo. Se toma el usuario con el que se autentico
		String username = ((User) authResult.getPrincipal()).getUsername();
		
		Collection<? extends GrantedAuthority> roles =  authResult.getAuthorities();
		
		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(username)//authResult.getName() //Usuario
				.signWith(SignatureAlgorithm.HS512, "A.Very.Secret.P@ssw0rd".getBytes())
				.setIssuedAt(new Date()) //Fecha de creacion
				.setExpiration(new Date(System.currentTimeMillis() + 3600000L)) //Fecha de expiracion
				.compact();
		
		response.addHeader("Authorization", "Bearer " + token);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("token", token);
		body.put("user", (User) authResult.getPrincipal());
		body.put("mensaje", String.format("Hola %s, has iniciado sesion con exito!", username));
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
		
	}



	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("mensaje", "Error de autenticacion: username o password incorrecto");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
	}
	
	
}
