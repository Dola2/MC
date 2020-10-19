package com.cts.mc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.mc.model.RegistrationModel;
@Service
@Configuration
public class RestTemplateConfig {
private RestTemplate restTemplate;

@Autowired
CommonConfig config;


@Bean
public RestTemplate restTemplate(RestTemplateBuilder builder) {
	
	this.restTemplate = builder.build();
   
   return restTemplate;
}


public String getRestCall(Integer custId) {
	
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.setBasicAuth(config.getRegusername(), config.getRegpassword());
    HttpEntity < Integer > entity = new HttpEntity < Integer > (custId, headers);
    
	    ResponseEntity < RegistrationModel > response = restTemplate.exchange(config.getCheckCustCredUrl(), HttpMethod.GET, entity,RegistrationModel.class,custId);
	   
	    RegistrationModel model = response.getBody();
	    HttpEntity < String> loginEntity = new HttpEntity < String> (model.getUserName(), headers);
	    ResponseEntity < String > token = restTemplate.exchange(config.getLoginUrl(), HttpMethod.GET, loginEntity,String.class,model.getUserName(),model.getPassword());
	   	
	   	 return token.getBody();
   
   
}
}
