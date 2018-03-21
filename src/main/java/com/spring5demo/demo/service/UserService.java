package com.spring5demo.demo.service;

import com.spring5demo.demo.domain.Authority;
import com.spring5demo.demo.domain.User;

public interface UserService {
	void save(User user);
    void enabled(String username);
    User findOneByUsername(String username);
    void saveAuthority(String roleName);
    Authority findOneByName(String roleName);
    User activateRegistration(String key);
    public void sendOrderConfirmation(User user);
}
