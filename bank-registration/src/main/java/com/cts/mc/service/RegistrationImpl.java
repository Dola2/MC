package com.cts.mc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.mc.entity.RegistrationEntity;
import com.cts.mc.interfaces.RegistrationRepository;
import com.cts.mc.interfaces.RegistrationService;
import com.cts.mc.model.RegistrationModel;
@Service
public class RegistrationImpl implements RegistrationService{	
	
	@Autowired
	RegistrationRepository repo;
	
	
	
	@Override
	public RegistrationEntity create(RegistrationModel model) {
		ModelMapper modelMapper = new ModelMapper();

		RegistrationEntity entity = modelMapper.map(model,RegistrationEntity.class);
		
		return repo.save(entity);		 	
	}
	
	@Override
	public void delete(RegistrationEntity entity) {
		repo.deleteById(entity.getCustId());		
	}
	@Override
	public List<RegistrationEntity> findAll() {		
		return (List<RegistrationEntity>)  repo.findAll();
	}


	@Override
	public Optional<RegistrationEntity> findByCustId(Integer id) {		
		return repo.findById(id);
	}


	@Override
	public RegistrationEntity update(RegistrationEntity entity,RegistrationModel model ) {		
		
			entity.setEmail(model.getEmail());
			entity.setContactNo(model.getContactNo());
			entity.setName(model.getName());
			entity.setCity(model.getCity());
			entity.setState(model.getState());
			entity.setAddress(model.getAddress());
			return entity;
		
	}

	@Override
    public String login(String username, String password) {
        Optional<RegistrationEntity> entity = repo.findByUserNameAndPassword(username,password);
        if(entity.isPresent()){
            String token = UUID.randomUUID().toString();           
            entity.get().setToken(token);
            entity.get().setIslogin("Y");
            repo.save(entity.get());
            return token;
        }

        return StringUtils.EMPTY;
    }


	@Override
	public Boolean checkUserName(String userName) throws UsernameNotFoundException {
			Boolean status = false;
			if(repo.checkUserName(userName).isPresent()) {
				status = true;
				
			}
			
			return status;
		
		
	}
	@Override
    public  Optional<User> findByToken(String token) {
        Optional<RegistrationEntity> entity= repo.findByToken(token);
        if(entity.isPresent()){           
            User user= new User(entity.get().getUserName(), entity.get().getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(user);
        }
        return  Optional.empty();
    }


	@Override
	public Boolean isLogin(String username, String password) {
		 Optional<RegistrationEntity> entity = repo.checkUserName(username);
		 Boolean status = false;
		 if(entity.isPresent() && entity.get().getIslogin().equals("Y")){ 
			 status = true;
		 }
		return status;
	}

	@Override
	public Boolean logOff(String userName) {
		Boolean status = false;
		Optional<RegistrationEntity> entity= repo.checkUserName(userName);
		if(entity.isPresent()){
			entity.get().setToken(null);
            entity.get().setIslogin("N");
            repo.save(entity.get());
            status = true;
		}
		return status;
	}


	





	

	


	



}
