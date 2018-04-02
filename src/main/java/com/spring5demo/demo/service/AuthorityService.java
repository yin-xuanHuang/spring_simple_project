package com.spring5demo.demo.service;

import com.spring5demo.demo.domain.Authority;

public interface AuthorityService {
	
	Authority findOneByName(String roleName);
	void save(String roleName);
}
