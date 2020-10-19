package com.cts.mc.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cts.mc.entity.LoanApplyEntity;
@Repository
public interface LoanApplyRepository extends CrudRepository<LoanApplyEntity,Integer> {
	
	}
