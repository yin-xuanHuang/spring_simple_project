package com.spring5demo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring5demo.demo.domain.User;
import com.spring5demo.demo.dto.UserPassword;
import com.spring5demo.demo.repository.UserRepository;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	@Override
	public int save(User user) {
		User existUser = this.userRepository.findOneByUsername(user.getUsername());
		if(existUser != null) {
			return 1;
		}
		
		existUser = this.userRepository.findOneByEmail(user.getEmail());
		if(existUser != null) {
			return 2;
		}
		
		this.userRepository.save(user);
		
		return 0;

	}

	@Override
	public User findOneByUsername(String username) {
		return this.userRepository.findOneByUsername(username);
	}
	
	@Override
	public User findOneByEmail(String email) {
		return this.userRepository.findOneByEmail(email);
	}
	
	@Override
	public boolean passwordReset(User checkUser, UserPassword user) {
		return this.userRepository.passwordReset(checkUser, user);
	}
	
	@Override
	public User activateRegistration(String key) {
		return this.userRepository.activateRegistration(key);
	}

	@Override
	public String passwordForget(User checkUser) {
		return this.userRepository.passwordReset(checkUser);
			
	}
	
}
