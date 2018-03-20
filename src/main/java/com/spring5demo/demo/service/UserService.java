package com.spring5demo.demo.service;

import com.spring5demo.demo.domain.User;

public interface UserService {
	void save(User user);
    void enabled(String username);
    User findByUsername(String username);
    void saveAuthority(String roleName);
}
