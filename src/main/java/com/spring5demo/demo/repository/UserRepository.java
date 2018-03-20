package com.spring5demo.demo.repository;

import com.spring5demo.demo.domain.User;

public interface UserRepository {

	User findByUsersname(String username);
	User save(User user);
}
