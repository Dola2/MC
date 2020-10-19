package com.cts.mc.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cts.mc.entity.RegistrationEntity;
import com.cts.mc.model.RegistrationModel;

public interface RegistrationService {
	public RegistrationEntity create(RegistrationModel registrationEntity);
	public void delete(RegistrationEntity entity);
	public List<RegistrationEntity> findAll();
	public Optional<RegistrationEntity> findByCustId(Integer id);
	public RegistrationEntity update(RegistrationEntity entity,RegistrationModel model);
	public String login(String username, String password);
	public Boolean checkUserName(String userName) throws UsernameNotFoundException;
	public Optional<User> findByToken(String token);
	public Boolean isLogin(String username, String password);
	
	public Boolean logOff(String userName);

}
