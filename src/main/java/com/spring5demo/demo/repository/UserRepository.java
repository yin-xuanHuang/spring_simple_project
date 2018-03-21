package com.spring5demo.demo.repository;

import com.spring5demo.demo.domain.User;

public interface UserRepository {

	User findOneById(Long id);
	User findOneByUsername(String username);
	User save(User user);
	User activateRegistration(String key);
	
}
