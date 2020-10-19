package com.cts.mc.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	@Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
       

    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("cts.com");
        super.afterPropertiesSet();
    }

}
