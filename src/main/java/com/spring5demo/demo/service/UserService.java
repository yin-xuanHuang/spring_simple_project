package com.spring5demo.demo.service;

import com.spring5demo.demo.domain.Authority;
import com.spring5demo.demo.domain.User;
import com.spring5demo.demo.dto.UserPassword;

public interface UserService {
	User findOneByUsername(String username);
	User findOneByEmail(String email);
	Authority findOneByName(String roleName);
	
	User activateRegistration(String key);
	
	void save(User user);
    void saveAuthority(String roleName);
    
    void sendOrderConfirmation(User user);
	void sendPasswordReset(User checkUser);
	boolean passwordReset(User checkUser, UserPassword user);
}
