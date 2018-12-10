package com.dasetova.springstudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dasetova.springstudy.auth.filter.JWTAuthenticationFilter;
import com.dasetova.springstudy.auth.filter.JWTAuthorizationFilter;
import com.dasetova.springstudy.auth.service.JWTService;
//import com.dasetova.springstudy.auth.handler.LoginSuccessHandler;
import com.dasetova.springstudy.models.service.JpaUserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
//	@Autowired
//	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	private JWTService jwtService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/list**", "/locale").permitAll()
//		.antMatchers("/show/**", "/uploads/**").hasAnyRole("USER")
//		.antMatchers("/form/**", "/delete/**", "/bill/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
//		.and().formLogin().successHandler(successHandler).loginPage("/login").permitAll() //Creates the login and manages the unauthorized
//		.and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/error_403")
		.and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager(), this.jwtService))
		.addFilter(new JWTAuthorizationFilter(authenticationManager(), this.jwtService))
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception{
		build.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}
}
