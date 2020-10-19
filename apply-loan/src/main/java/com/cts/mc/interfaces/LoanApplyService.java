package com.cts.mc.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.cts.mc.entity.LoanApplyEntity;
import com.cts.mc.model.LoanApplyModel;

@Service
public interface LoanApplyService  {
	public List<LoanApplyEntity> findAll();	
	public Optional<LoanApplyEntity> findByLoanId(Integer loanId);
	public LoanApplyEntity update(LoanApplyModel requesEntity);
	public LoanApplyEntity create(LoanApplyModel entity);
	public void delete(LoanApplyEntity entity);
	public String isLogin(Integer custId) throws HttpClientErrorException;
}
