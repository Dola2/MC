package com.cts.mc.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.mc.entity.RegistrationEntity;
@Repository

public interface RegistrationRepository extends CrudRepository<RegistrationEntity,Integer> {

	@Query("select r from RegistrationEntity r where r.userName =:userName")
	public Optional<RegistrationEntity> checkUserName(@Param("userName") String userName);
	@Query("select r from RegistrationEntity r where r.userName=:userName  and r.password=:password")
	public Optional<RegistrationEntity> login(@Param("userName")String userName,@Param("password")String password);
	public Optional<RegistrationEntity> findByToken(String token);
	
}
