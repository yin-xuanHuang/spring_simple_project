package com.spring5demo.demo.repository;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring5demo.demo.domain.User;

public class HibernateUserRepository implements UserRepository {

	private SessionFactory sessionFactory;
	
	private PasswordEncoder passwordEncoder;

	public HibernateUserRepository(SessionFactory sessionFactory, PasswordEncoder passwordEncoder) {
		this.sessionFactory = sessionFactory;
		this.passwordEncoder = passwordEncoder;
	}
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public User findByUsersname(String username) {
		return (User) currentSession().get(User.class, username);
	}

	@Override
	public User save(User user) {
		String password = user.getPassword();
		user.setPassword(passwordEncoder.encode(password));
		
		Serializable id = currentSession().save(user);
		return new User((Long) id,
				user.getUsername(),
				user.getEmail(),
				user.isEnabled());
	}
	
	

}
