package com.cts.mc.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_apply")
public class LoanApplyEntity {
			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)	    
			@Column(name = "loan_id")
			private Integer loanId;
			
			@Column(name = "cust_id")
			private Integer custId;
			
			@Column(name = "loan_type")
			private String loanType;
			
			@Column(name = "loan_amt")
			private BigDecimal loanAmount; 
			
			@Column(name = "int_rate")
			private Double intRate;
			
			@Column(name = "tenure")
			private Double tenure;
	
		   @JsonIgnoreProperties		  
		   @CreationTimestamp		  
		   @Column(name = "created_on") 
		   private Timestamp created_On;
		   
		   @JsonIgnoreProperties		  
		   @UpdateTimestamp		  
		   @Column(name = "updated_on") 
		   private Timestamp updated_On;
	 
	  
	 
	
	
}
