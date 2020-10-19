package com.cts.mc.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.mc.configuration.RestTemplateConfig;
import com.cts.mc.entity.LoanApplyEntity;
import com.cts.mc.interfaces.LoanApplyRepository;
import com.cts.mc.interfaces.LoanApplyService;
import com.cts.mc.model.LoanApplyModel;
@Service
public class LoanApplyImpl implements LoanApplyService{

	@Autowired
	LoanApplyRepository service;
	
	@Autowired	
	RestTemplateConfig restTemplate;

	public List<LoanApplyEntity> findAll() {
        return (List<LoanApplyEntity>) service.findAll();
    }
	
	public LoanApplyEntity create(LoanApplyModel model) {
		ModelMapper modelMapper = new ModelMapper();
		LoanApplyEntity entity = modelMapper.map(model,LoanApplyEntity.class);
		return service.save(entity);
	}

	public Optional<LoanApplyEntity> findByLoanId(Integer loanId){
		
		return service.findById(loanId);
	}
	public void delete(LoanApplyEntity entity) {
		service.delete(entity);
	}
	public LoanApplyEntity update(LoanApplyModel requesEntity) {
		Optional<LoanApplyEntity> entity = service.findById(requesEntity.getLoanId());
		if(entity.isPresent()) {
			entity.get().setLoanType(requesEntity.getLoanType());
			entity.get().setLoanAmount(requesEntity.getLoanAmount());
			entity.get().setIntRate(requesEntity.getIntRate());
			entity.get().setTenure(requesEntity.getTenure());
			entity.get().setCustId(requesEntity.getCustId());
			service.save(entity.get());
			
		}
		return entity.get();
	}

	@Override
	public String isLogin(Integer custId) {			
		
		return restTemplate.getRestCall(custId);
	}
}
