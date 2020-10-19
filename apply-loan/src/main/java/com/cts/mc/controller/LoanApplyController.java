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

import com.cts.mc.entity.LoanApplyEntity;
import com.cts.mc.interfaces.LoanApplyService;
import com.cts.mc.model.LoanApplyModel;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("cts/mc/loan/")
public class LoanApplyController {
	

	@Autowired
	LoanApplyService impl ;
	
	private final static String LOAN_NOT_FOUND = "Loan Not Found";
	private final static String LOGIN_DONE = "Do Login first";
	
	@PostMapping(value = "create",consumes = "application/json")
	public @ResponseBody ResponseEntity<String> createLoanApply(@RequestBody LoanApplyModel request){
		
		try {
		String tokenPresent = impl.isLogin(request.getCustId());
		log.info("IN loan create method",tokenPresent);
		if(!tokenPresent.isEmpty()){
			impl.create(request);
			log.info("Customer already Logged in");
			return new ResponseEntity<>("Loan Applied Successfully",HttpStatus.CREATED);	
		}
		}catch(Exception e) {
			log.error("Customer not Logged in", e);
			return new ResponseEntity<>("LOGIN_DONE",HttpStatus.NOT_FOUND);		
		}
		return new ResponseEntity<>(LOGIN_DONE,HttpStatus.UNAUTHORIZED);	
				
		
	}
	
	@GetMapping(value = "getLoan",produces =  "application/json")
	public @ResponseBody ResponseEntity<List<LoanApplyEntity>> getLoans(){	
		
		List<LoanApplyEntity> loanList = impl.findAll();		
		if(loanList.isEmpty()) {
			log.info(LOAN_NOT_FOUND);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(loanList,HttpStatus.OK);
		}
		
	}
	
	@GetMapping(value = "getLoan/{loanId}",produces =  "application/json")
	public @ResponseBody ResponseEntity<LoanApplyEntity> getLoanById(@PathVariable(name = "loanId") Integer id){		
			
				Optional<LoanApplyEntity> entity = impl.findByLoanId(id);
				if(entity.isPresent()) {
					log.info("Loan Record  Found");					
					return new ResponseEntity<>(entity.get(),HttpStatus.OK);	
				}	
				else {
					log.info(LOAN_NOT_FOUND);
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
		
	}
	
	@DeleteMapping(value = "deleteLoan/{id}")	
	public @ResponseBody ResponseEntity<String> deleteLoanById(@PathVariable(name = "id") Integer id){	
		
				Optional<LoanApplyEntity> entity = impl.findByLoanId(id);
				if(entity.isPresent()) {
					impl.delete(entity.get());
					log.info("Loan Deleted Successfully");
					return new ResponseEntity<>("Loan Deleted Successfully",HttpStatus.OK);	
				}	
				else {
					log.info(LOAN_NOT_FOUND);
					return new ResponseEntity<>(LOAN_NOT_FOUND,HttpStatus.NOT_FOUND);
				}
					
					
				
	}
	@PutMapping(value = "updateLoan",consumes = "application/json")
	
	public @ResponseBody ResponseEntity<String> modifyLoanById(@RequestBody LoanApplyModel request){	
		
			Optional<LoanApplyEntity> entity = impl.findByLoanId(request.getLoanId());
			if(!entity.isEmpty()){
				impl.update(request);
				log.info("Loan Updated Successfully");
				return new ResponseEntity<>("Loan Updated Successfully",HttpStatus.OK);	
			}
			else {
				log.info(LOAN_NOT_FOUND);
				return new ResponseEntity<>(LOAN_NOT_FOUND,HttpStatus.NOT_FOUND);
			}
	}		
}
