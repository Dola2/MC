
  package com.cts.mc.configuration;
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import  org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import  org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import  org.springframework.security.config.annotation.web.configuration. WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cts.mc.service.CustomAuthenticationEntryPoint;
  
  @Configuration  
  @EnableWebSecurity 
  public class SecurityConfiguration extends  WebSecurityConfigurerAdapter{
  
	  @Autowired
	  private CommonConfig config;
	 private PasswordEncoder encoder =  PasswordEncoderFactories.createDelegatingPasswordEncoder();  
  @Autowired 
  public void configureGlobal(AuthenticationManagerBuilder auth) throws  Exception 
  { auth.inMemoryAuthentication().withUser(config.getUsername())
	  .password(encoder.encode(config.getPassword())).roles(config.getRole());
  }
  
  @Override 
  public void configure(WebSecurity web) throws Exception {
  web.ignoring().antMatchers("/.html")
  .antMatchers("/swagger-ui.html")
    .antMatchers("/webjars/springfox-swagger-ui/**")
   .antMatchers("/swagger-resources/**")
  .antMatchers("/v2/api-docs");
  
  }

@Override
  protected void configure(HttpSecurity http) throws Exception {
	  http.csrf().disable() 
	.authorizeRequests()
	 .anyRequest().authenticated().and()	
	 .httpBasic().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

	  
	 
  }
  
  
  }
 