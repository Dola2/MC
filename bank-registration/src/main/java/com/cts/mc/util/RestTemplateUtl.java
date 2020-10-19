package com.cts.mc.util;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@Configuration
public class RestTemplateUtl {
	private RestTemplate restTemplate;

@Bean
public RestTemplate restTemplate(RestTemplateBuilder builder) {
	
	this.restTemplate = builder.build();
   
   return restTemplate;
}

public String postRestCall(RequestEntity<?> request) {	
			 
			  ResponseEntity<String> response = restTemplate.exchange(request, String.class);
			  return response.getBody();
			 
 }

}
