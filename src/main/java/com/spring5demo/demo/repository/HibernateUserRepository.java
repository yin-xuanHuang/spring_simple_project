package com.spring5demo.demo.repository;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring5demo.demo.domain.User;
import com.spring5demo.demo.util.RandomUtil;

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
		
		if(user.isEnabled())
		{
			user.setActivationKey(null);
		}else {
			user.setActivationKey(RandomUtil.generateActivationKey());
		}
		
		Serializable id = currentSession().save(user);
		
		User newUser =  new User((Long) id,
								user.getUsername(),
								user.getEmail(),
								user.isEnabled(),
								user.getActivationKey());
		
		log.debug("Create User: {}", newUser);
		
		return newUser;
		
	}

	@Override
	public User activateRegistration(String key) {
		User user = (User)currentSession()
							.createQuery("from User where activation_key=?")
							.setParameter(0, key)
							.uniqueResult();
		
		if(user == null) {
			return null;
		} else {
			user.setActivationKey(null);
			user.setEnabled(true);
			currentSession().update(user);
			
			log.debug("Enabled for User: {}", user);
			
			return user;
		}
	}
	
	
}
