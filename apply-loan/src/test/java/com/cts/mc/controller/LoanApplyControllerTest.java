package com.cts.mc.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cts.mc.entity.LoanApplyEntity;
import com.cts.mc.exception.LoanNotFoundException;
import com.cts.mc.interfaces.LoanApplyService;
import com.cts.mc.model.LoanApplyModel;



@ExtendWith(MockitoExtension.class)
public class LoanApplyControllerTest {
	

	@Mock
	LoanApplyService impl ;
	
	@InjectMocks
	LoanApplyController controller;
	@Test
	public void createLoanApplyPositiveTest(){
		LoanApplyModel entity = new LoanApplyModel();
		entity.setCustId(123);
		entity.setLoanAmount(new BigDecimal(1234.00));
		entity.setIntRate(3.4);
		entity.setTenure(10.0);
		when(impl.isLogin(entity.getCustId())).thenReturn("ABC");
		ResponseEntity<String> responseEntity = controller.createLoanApply(entity);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
		
	}
	@Test
	public void createLoanApplyNegvTest(){
		LoanApplyModel entity = new LoanApplyModel();
		entity.setCustId(123);
		entity.setLoanAmount(new BigDecimal(1234.00));
		entity.setIntRate(3.4);
		entity.setTenure(10.0);
		when(impl.isLogin(entity.getCustId())).thenReturn("");
		ResponseEntity<String> responseEntity = controller.createLoanApply(entity);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(401);
		
	}
	@Test()
	public void getLoansPositiveTest() throws LoanNotFoundException{	
		LoanApplyEntity entity = new LoanApplyEntity();
		entity.setCustId(123);
		entity.setLoanAmount(new BigDecimal(123445.00));
		entity.setIntRate(3.4);
		entity.setTenure(10.0);
		
		LoanApplyEntity entity1 = new LoanApplyEntity();
		entity1.setCustId(12368);
		entity1.setLoanAmount(new BigDecimal(1234.00));
		entity1.setIntRate(6.4);
		entity1.setTenure(10.0);
		
		List<LoanApplyEntity> loanList = new ArrayList<LoanApplyEntity>();
		loanList.add(entity);
		loanList.add(entity1);
		
		when(impl.findAll()).thenReturn(loanList);
		
		ResponseEntity<List<LoanApplyEntity>> responseEntity = controller.getLoans();
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		
	}
	
	@Test
	public void getLoansNegetiveTest() throws LoanNotFoundException{			
		List<LoanApplyEntity> loanList = new ArrayList<LoanApplyEntity>();		
		when(impl.findAll()).thenReturn(loanList);		
		ResponseEntity<List<LoanApplyEntity>> responseEntity = controller.getLoans();
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
		
	}
	
	@Test
	public void getLoanByIdPstvTest() throws LoanNotFoundException{	
		int id = 123;
		LoanApplyEntity entity = new LoanApplyEntity();
		entity.setLoanAmount(new BigDecimal(1234.00));
		entity.setIntRate(3.4);
		entity.setTenure(10.0);
		entity.setLoanId(123);
		when(impl.findByLoanId(id)).thenReturn(Optional.of(entity));
		ResponseEntity<LoanApplyEntity> responseEntity = controller.getLoanById(id);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	@Test
	public void getLoanByIdNgTest() throws LoanNotFoundException{	
		int id = 123;				
		when(impl.findByLoanId(id)).thenReturn(Optional.empty());		
		ResponseEntity<LoanApplyEntity> responseEntity = controller.getLoanById(id);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public  void deleteLoanByIdPtvTest() throws LoanNotFoundException{
		int id = 123;
		LoanApplyEntity entity = new LoanApplyEntity();
		entity.setLoanAmount(new BigDecimal(1234.00));
		entity.setIntRate(3.4);
		entity.setTenure(10.0);
		entity.setLoanId(123);
		when(impl.findByLoanId(id)).thenReturn(Optional.of(entity));
		ResponseEntity<?> responseEntity = controller.deleteLoanById(id);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	@Test
	public  void deleteLoanByIdNtvTest() throws LoanNotFoundException{
		int id = 123;				
		when(impl.findByLoanId(id)).thenReturn(Optional.empty());	
		ResponseEntity<?> responseEntity = controller.deleteLoanById(id);		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
	}
	@Test
	public void modifyLoanByIdPtvTest(){	
		
		LoanApplyModel entity = new LoanApplyModel();
		entity.setCustId(123);
		entity.setLoanAmount(new BigDecimal(1234.00));
		entity.setIntRate(3.4);
		entity.setTenure(10.0);
		entity.setLoanId(12389);
		
		when(impl.findByLoanId(entity.getLoanId())).thenReturn(Optional.of(new LoanApplyEntity()));
		ResponseEntity<?> responseEntity = controller.modifyLoanById(entity);		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);	
	}
	@Test
	public void modifyLoanByIdNtvTest(){	
		
		LoanApplyModel entity = new LoanApplyModel();
		entity.setCustId(123);
		entity.setLoanAmount(new BigDecimal(1234.00));
		entity.setIntRate(3.4);
		entity.setTenure(10.0);
		entity.setLoanId(12389);
		when(impl.findByLoanId(entity.getLoanId())).thenReturn(Optional.empty());
		ResponseEntity<?> responseEntity = controller.modifyLoanById(entity);		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);	
	}
}
