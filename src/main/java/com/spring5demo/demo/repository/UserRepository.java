package com.spring5demo.demo.repository;

import com.spring5demo.demo.domain.User;
import com.spring5demo.demo.dto.UserPassword;

public interface UserRepository {

	User findOneById(Long id);
	User findOneByUsername(String username);
	User findOneByEmail(String email);
	String passwordReset(User user);
	boolean passwordReset(User checkUser, UserPassword user);
	User save(User user);
	User activateRegistration(String key);	
}
