package com.spring5demo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring5demo.demo.domain.AuthoritiesConstants;
import com.spring5demo.demo.domain.Authority;
import com.spring5demo.demo.domain.User;
import com.spring5demo.demo.repository.AuthorityRepository;
import com.spring5demo.demo.repository.UserRepository;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	
	private AuthorityRepository authorityRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository){
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
	}
	
	@Override
	public void save(User user) {
		Authority authority = this.authorityRepository.findOneByName(AuthoritiesConstants.USER);
        user.addAuthority(authority);

		this.userRepository.save(user);

	}

	@Override
	public void enabled(String username) {
		User user = findOneByUsername(username);
		user.setEnabled(true);
		this.userRepository.save(user);

	}

	@Override
	public User findOneByUsername(String username) {
		return this.userRepository.findOneByUsername(username);
	}
	
	@Override
	public void saveAuthority(String roleName) {
		this.authorityRepository.save(roleName);
	}

	@Override
	public Authority findOneByName(String roleName) {
		return this.authorityRepository.findOneByName(roleName);
	}
	
	

}
