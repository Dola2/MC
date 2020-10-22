package com.cts.mc.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registration")
@ApiModel(description = "All details about the Customer. ")
public class RegistrationEntity implements Serializable {
	
	private static final long serialVersionUID = -2514342975295193094L;

		@ApiModelProperty(notes = "The database generated ID")
		@javax.persistence.Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "cust_id")
		private Integer custId;
		
		private String name;
		@Column(name = "user_name")
		private String userName;
		@Column(name = "password")
	  private String password;
	  @Column(name = "address")
	  private String address;
	  @Column(name = "state")
	  private String state;
	  @Column(name = "city")
	  private String city;
	  @Column(name = "country")
	  private String country; 
	  @Column(name = "email")
	  private String email; 
	  @Column(name = "pan")
	  private String pan;
	  @Column(name = "dob")
	  private String dob; 
	  @Column(name = "account_type")
	  private String accountType; 
	  @Column(name = "contact_no")
	  private String contactNo;
	  @Column(name = "token")
	  private String token;
	
	  @Column(name = "islogin")
	  private String islogin;
}
