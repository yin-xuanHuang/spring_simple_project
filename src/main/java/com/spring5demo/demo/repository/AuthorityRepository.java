package com.spring5demo.demo.repository;

import com.spring5demo.demo.domain.Authority;

public interface AuthorityRepository {

	Authority findOneByName(String roleName);
	void save(String roleName);

}
