package com.spring5demo.demo.service;

import com.spring5demo.demo.domain.User;
import com.spring5demo.demo.dto.UserPassword;

public interface UserService {
	User findOneByUsername(String username);
	User findOneByEmail(String email);
	
	User activateRegistration(String key);
	
	int save(User user);

	String passwordForget(User checkUser);
	boolean passwordReset(User checkUser, UserPassword user);
}
