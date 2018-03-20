package com.spring5demo.demo.repository;

import com.spring5demo.demo.domain.Authority;

public interface AuthorityRepository {

	Authority findOne(String roleName);
	void save(String roleName);

}
