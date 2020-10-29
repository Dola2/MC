package com.cts.mc.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.mc.entity.RegistrationEntity;
import com.cts.mc.exception.CustomerNotFoundException;
import com.cts.mc.exception.CustomerNotLoggedInException;
import com.cts.mc.interfaces.RegistrationService;
import com.cts.mc.model.RegistrationModel;
import com.cts.mc.util.MessageSenderToQueue;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@Api(value = "Customer Registration System")
@RequestMapping("/cts/mc/customer/")
public class RegistrationController {
	
	@Autowired
	private RegistrationService service ;
	@Autowired
	MessageSenderToQueue queue;
	
	@Autowired
	private Gson gson;
	
	private static final  String   CUST_NOTFOUND = "Customer Not Found";
	private  static final  String  CUST_LOG_OFF = "Customer Logged Off";
	private static final  String  CUST_NOT_LOGIN = "Customer not Logged in";
	private static final  String  CUST_LOGIN = "Customer  Logged in";
	
	
	@ApiOperation(value = "List All Customer")
	@GetMapping(value = "getCustomer",produces =  "application/json")
	public @ResponseBody ResponseEntity<List<RegistrationEntity>> getCustomers() throws CustomerNotFoundException{	
		
		List<RegistrationEntity> entity = service.findAll();
		if(null!=entity) {
			log.info("Number Of Costomer Found "+entity.size());
			return new ResponseEntity<>(entity,HttpStatus.OK);
		}	
		else {
		log.error(CUST_NOTFOUND, HttpStatus.NOT_FOUND);	
		throw new CustomerNotFoundException();	
		}
	}
	@ApiOperation(value = "Show One Customer")
	@GetMapping(value = "getCustomer/{Id}",produces =  "application/json")
	public @ResponseBody ResponseEntity<String> getCustomerById(@ApiParam(value = "Customer Id to Modify", required = true)@PathVariable(name = "Id") Integer id) throws CustomerNotFoundException, CustomerNotLoggedInException{	
				
			Optional<RegistrationEntity> entity = service.findByCustId(id);
			if(entity.isPresent()) {	
				boolean  status = service.isLogin(entity.get().getUserName(),entity.get().getPassword());
				if(!status) {
					throw new CustomerNotLoggedInException();
				
				}else {
					
					return new ResponseEntity<>(gson.toJson(entity.get()),HttpStatus.OK);
				}
			}else {
				log.error(CUST_NOTFOUND);
				throw new CustomerNotFoundException();
			}
			
	}
	@ApiOperation(value = "Delete One Customer")
	@DeleteMapping(value = "deleteCustomer/{Id}",produces =  "application/json")
	public @ResponseBody ResponseEntity<String> deleteCustomerById(@ApiParam(value = "Customer Id to Delete", required = true) 
	@PathVariable(name = "Id") Integer id) throws CustomerNotFoundException, CustomerNotLoggedInException{	
		
		Optional<RegistrationEntity> entity = service.findByCustId(id);		
		if(entity.isPresent()) {
			boolean  status = service.isLogin(entity.get().getUserName(),entity.get().getPassword());
			if(!status) {
				log.error(CUST_NOT_LOGIN);
				throw new CustomerNotLoggedInException();
			
			}else {
			service.delete(entity.get());
			log.error("Customer Delete Successful");	
			return new ResponseEntity<>("Customer Delete Successful",HttpStatus.OK);
			}
		}
		else {
			log.error(CUST_NOTFOUND, HttpStatus.NOT_FOUND);	
			throw new CustomerNotFoundException();
		}
	}
	@ApiOperation(value = "Register Customer")
	@PostMapping(value = "registration",consumes = "application/json")
	public @ResponseBody ResponseEntity<String> registerCustomer(@ApiParam(value = "Customer Registration")@RequestBody RegistrationModel payLoad) {
		String msg =  "please do login first";
		boolean  status = service.checkUserName(payLoad.getUserName());	
		
		if(!status)	{
			
			service.create(payLoad);
			log.info("Customer Registration Done");
			msg="Customer Registration Done";
		}
		
		return new ResponseEntity<>(msg,HttpStatus.CREATED);
	}
	@ApiOperation(value = "Login Customer")
	@GetMapping(value = "login/{usernm}/{passwrd}",consumes = "application/json")
	public @ResponseBody ResponseEntity<String> loginCustomer(@ApiParam(value = "Customer UserName")@PathVariable(name = "usernm") String userNm,
			@ApiParam(value = "Customer Password")@PathVariable(name = "passwrd") String password) throws CustomerNotFoundException {
		boolean  status = service.isLogin(userNm, password);
		if(!status) {
			service.login(userNm, password);
			log.info(CUST_LOGIN);
			return new ResponseEntity<>(CUST_LOGIN,HttpStatus.OK);		
		}
		else {
			log.info("Customer not Registerred");
			throw new CustomerNotFoundException();
		}
		
	}
	@ApiOperation(value = "Update Customer")
	@PutMapping(value = "updateAccount",consumes = "application/json")	
	public @ResponseBody ResponseEntity<String> modifyAccountById(@RequestBody RegistrationModel request) throws CustomerNotLoggedInException, CustomerNotFoundException{			
			
				Optional<RegistrationEntity> entity = service.findByCustId(request.getCustId());
				
				if(entity.isPresent()) {
					boolean  status = service.isLogin(request.getUserName(), request.getPassword());
					if(!status) {
						log.info(CUST_NOT_LOGIN);
						throw new CustomerNotLoggedInException();				
					}else {
						service.update(entity.get(),request);
						log.info("Account Updated Successfully");
						return new ResponseEntity<>("Account Updated Successfully",HttpStatus.OK);
					}				
				
				}else {
					log.info(CUST_NOTFOUND);
					throw new CustomerNotFoundException();
				}
			
	}
	@ApiOperation(value = "Log of Customer")
	@GetMapping(value = "logoff/{usernm}")
	public @ResponseBody ResponseEntity<String> logOffCustomer(@ApiParam(value = "Customer UserName")@PathVariable(name = "usernm") String userNm) throws CustomerNotFoundException {
		boolean  status = service.logOff(userNm);
		
		if(status) {
			
			log.info(CUST_LOG_OFF);
			return new ResponseEntity<>(CUST_LOG_OFF,HttpStatus.OK);		
		}
		else {
			log.info(CUST_NOTFOUND);
			throw new CustomerNotFoundException();
		}
		
	}
}
