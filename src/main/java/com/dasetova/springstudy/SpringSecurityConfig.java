package com.dasetova.springstudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

import com.dasetova.springstudy.auth.handler.LoginSuccessHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private LoginSuccessHandler successHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/list").permitAll()
		.antMatchers("/show/**", "/uploads/**").hasAnyRole("USER")
		.antMatchers("/form/**", "/delete/**", "/bill/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().successHandler(successHandler).loginPage("/login").permitAll() //Creates the login and manages the unauthorized
		.and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/error_403");
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception{
		UserBuilder users = User.withDefaultPasswordEncoder();
		
		build.inMemoryAuthentication()
		.withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
		.withUser(users.username("dasetova").password("12345").roles("USER"));
	}
}
