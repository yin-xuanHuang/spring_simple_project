package com.spring5demo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring5demo.demo.domain.Authority;
import com.spring5demo.demo.repository.AuthorityRepository;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

	private AuthorityRepository authorityRepository;
	
	@Autowired
	public AuthorityServiceImpl(AuthorityRepository authorityRepository){	
		this.authorityRepository = authorityRepository;
	}
	
	@Override
	public Authority findOneByName(String roleName) {
		return this.authorityRepository.findOneByName(roleName);
	}

	@Override
	public void save(String roleName) {
		this.authorityRepository.save(roleName);
	}

}
