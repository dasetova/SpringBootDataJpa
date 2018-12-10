package com.dasetova.springstudy.auth.filter;

import java.io.IOException;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dasetova.springstudy.auth.service.JWTService;
import com.dasetova.springstudy.auth.service.JWTServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	
	private JWTService jwtService;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		super();
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
		this.jwtService = jwtService;
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
		String token = this.jwtService.create(authResult);
		
		response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("token", token);
		body.put("user", (User) authResult.getPrincipal());
		body.put("mensaje", String.format("Hola %s, has iniciado sesion con exito!", ((User) authResult.getPrincipal()).getUsername()));
		
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
