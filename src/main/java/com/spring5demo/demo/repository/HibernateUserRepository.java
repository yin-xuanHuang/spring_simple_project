package com.spring5demo.demo.repository;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring5demo.demo.domain.User;

public class HibernateUserRepository implements UserRepository {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
	public User findOneById(Long id) {
		return currentSession().get(User.class, id);
	}
	

	@Override
	public User findOneByUsername(String username) {
		return (User) currentSession()
							.createQuery("from User where username=?")
							.setParameter(0, username)
							.uniqueResult();
	}

	@Override
	public User save(User user) {
		User existUser = findOneByUsername(user.getUsername());
		
		if(existUser !=  null) {
			return existUser;
		}
		
		String password = user.getPassword();
		user.setPassword(passwordEncoder.encode(password));
		
		Serializable id = currentSession().save(user);
		return new User((Long) id,
				user.getUsername(),
				user.getEmail(),
				user.isEnabled());
		
	}
}
