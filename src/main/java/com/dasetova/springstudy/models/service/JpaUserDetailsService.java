package com.dasetova.springstudy.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dasetova.springstudy.models.dao.IUserDAO;
import com.dasetova.springstudy.models.entity.Role;
import com.dasetova.springstudy.models.entity.User;

@Service
public class JpaUserDetailsService implements UserDetailsService{

	@Autowired
	private IUserDAO userDAO;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userDAO.findByUsername(username);
		
		if (user == null) {
			logger.error("Error login: Username doesnt exist: " + username);
			throw new UsernameNotFoundException("Username doesnt exist: " + username);
		}
		
		if (user.getRoles().isEmpty() ) {
			logger.error("Error login: user doesnt have roles: " + username);
			throw new UsernameNotFoundException("Username doesnt have roles: " + username);
		}
			
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(Role role: user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}

}
