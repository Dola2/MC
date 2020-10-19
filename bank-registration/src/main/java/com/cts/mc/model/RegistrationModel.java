package com.cts.mc.model;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class RegistrationModel {
	private Integer custId;		
	private String name;
	private String userName; 
  private String password;
  private String address;
  private String state;
  private String city;
  private String country; 
  private String email; 	  
  private String pan; 
  private String dob; 
  private String accountType; 
  private String contactNo;
  private String token;
  private String islogin;
	 
	
	
}
