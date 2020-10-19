package com.cts.mc.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class LoanApplyModel {
	private Integer custId;
	private Integer loanId;
	
	private String loanType;
	
	private BigDecimal loanAmount; 
	
	private Double intRate;
	
	private Double tenure;
	
	
	
}
