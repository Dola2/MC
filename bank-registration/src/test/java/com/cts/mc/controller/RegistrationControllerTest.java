package com.cts.mc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cts.mc.entity.RegistrationEntity;
import com.cts.mc.exception.CustomerNotFoundException;
import com.cts.mc.exception.CustomerNotLoggedInException;
import com.cts.mc.interfaces.RegistrationService;
import com.cts.mc.model.RegistrationModel;
@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {
	
	@Mock
	private RegistrationService service ;	
	
	@InjectMocks
	RegistrationController controller;
	
	@Test()
	public void getCustomersTest() throws CustomerNotFoundException{	
		
		RegistrationEntity registrationEntity = new RegistrationEntity();
		registrationEntity.setAccountType("Saving");
		registrationEntity.setAddress("Birati");
		registrationEntity.setEmail("dd@gmail.com");
			
		
		RegistrationEntity sregistrationEntity = new RegistrationEntity();
		sregistrationEntity.setAccountType("current");
		sregistrationEntity.setAddress("sarat colony");
		sregistrationEntity.setEmail("kakali@gmail.com");
		
		List<RegistrationEntity> list = new ArrayList<RegistrationEntity>();
		list.add(registrationEntity);
		list.add(sregistrationEntity);
		
		when(service.findAll()).thenReturn(list);
		
		
		ResponseEntity<List<RegistrationEntity>>  result =  controller.getCustomers();
		assertThat(result.getBody().size()).isEqualTo(2);
		assertThat(result.getBody().get(0).getEmail()).isEqualTo(registrationEntity.getEmail());
		assertThat(result.getBody().get(1).getEmail()).isEqualTo(sregistrationEntity.getEmail());
	}
	
	@Test
	public void getCustomerByIdTest() {	
		
		RegistrationEntity registrationEntity = new RegistrationEntity();
		registrationEntity.setCustId(546);
		registrationEntity.setAccountType("Saving");
		registrationEntity.setAddress("Birati");
		registrationEntity.setEmail("dd@gmail.com");
		
		when(service.findByCustId(registrationEntity.getCustId())).thenReturn(Optional.of(registrationEntity));
		
		assertThat(registrationEntity.getCustId()).isEqualTo(546);
		
		
	}
	@Test
	public void getCustomerByIdNgTest() throws CustomerNotFoundException, CustomerNotLoggedInException{	
		
		RegistrationEntity registrationEntity = new RegistrationEntity();
		registrationEntity.setCustId(546);
		registrationEntity.setAccountType("Saving");
		registrationEntity.setAddress("Birati");
		registrationEntity.setEmail("dd@gmail.com");
		
		when(service.findByCustId(registrationEntity.getCustId())).thenReturn(Optional.empty());
		
		ResponseEntity<?>  responseEntity =  controller.getCustomerById(registrationEntity.getCustId());
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
		
	}
	@Test
	public void deleteCustomerByIdTest() throws CustomerNotFoundException, CustomerNotLoggedInException 
	{	
		RegistrationEntity registrationEntity = new RegistrationEntity();
		registrationEntity.setCustId(546);
		registrationEntity.setAccountType("Saving");
		registrationEntity.setAddress("Birati");
		registrationEntity.setEmail("dd@gmail.com");		
		when(service.findByCustId(registrationEntity.getCustId())).thenReturn(Optional.of(registrationEntity));		
		ResponseEntity<?> responseEntity = controller.deleteCustomerById(registrationEntity.getCustId());
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		
		
	}
	@Test
	public void deleteCustomerByIdTestNg() throws CustomerNotFoundException, CustomerNotLoggedInException 
	{	
		RegistrationEntity registrationEntity = new RegistrationEntity();
		registrationEntity.setCustId(546);
		registrationEntity.setAccountType("Saving");
		registrationEntity.setAddress("Birati");
		registrationEntity.setEmail("dd@gmail.com");		
		when(service.findByCustId(registrationEntity.getCustId())).thenReturn(Optional.empty());		
		ResponseEntity<?> responseEntity = controller.deleteCustomerById(registrationEntity.getCustId());
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
		
		
	}
	@Test
	public void registerCustomerTest() {
		RegistrationModel registrationEntity = new RegistrationModel();
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		registrationEntity.setAccountType("Saving");
		registrationEntity.setAddress("Birati");
		registrationEntity.setEmail("dd@gmail.com");
		ResponseEntity<String> responseEntity = controller.registerCustomer(registrationEntity);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
	}
	

	@Test	
	public void modifyAccountTest() throws CustomerNotLoggedInException, CustomerNotFoundException{			
			
		RegistrationEntity registrationEntity = new RegistrationEntity();
		registrationEntity.setCustId(546);
		registrationEntity.setAccountType("Saving");
		registrationEntity.setAddress("Birati");
		registrationEntity.setEmail("dd@gmail.com");
		
		RegistrationModel model = new RegistrationModel()  ;
		model.setCustId(546);
		model.setAccountType("Credit");
		model.setAddress("Birati123");
		model.setEmail("dd@gmail123.com");
		
		when(service.findByCustId(registrationEntity.getCustId())).thenReturn(Optional.of(registrationEntity));		
		ResponseEntity<?> responseEntity = controller.modifyAccountById(model);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		
	}
	@Test	
	public void modifyAccountNgTest() throws CustomerNotLoggedInException, CustomerNotFoundException{			
			
		RegistrationEntity registrationEntity = new RegistrationEntity();
		registrationEntity.setCustId(546);
		registrationEntity.setAccountType("Saving");
		registrationEntity.setAddress("Birati");
		registrationEntity.setEmail("dd@gmail.com");
		
		RegistrationModel model = new RegistrationModel()  ;
		model.setCustId(546);
		model.setAccountType("Credit");
		model.setAddress("Birati123");
		model.setEmail("dd@gmail123.com");
		
		when(service.findByCustId(registrationEntity.getCustId())).thenReturn(Optional.empty());		
		
		// assertThat(controller.modifyAccountById(model).;
		
	}

}
