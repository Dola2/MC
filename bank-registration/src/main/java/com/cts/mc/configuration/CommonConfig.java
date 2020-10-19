package com.cts.mc.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Setter
@Getter
@PropertySource("classpath:bank-registration.properties")
@Configuration
public class CommonConfig {

@Value("${applyLoanCreateModuleUrl}")
private String applyLoanCreateModuleUrl;
@Value("${basic.auth.username}")
private String username;
@Value("${basic.auth.password}")
private String password;
@Value("${basic.auth.role}")
private String role;
}
