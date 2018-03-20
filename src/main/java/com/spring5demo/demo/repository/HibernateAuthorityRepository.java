package com.spring5demo.demo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.spring5demo.demo.domain.Authority;

public class HibernateAuthorityRepository implements AuthorityRepository {

	private SessionFactory sessionFactory;

	public HibernateAuthorityRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Authority findOne(String name) {
		return (Authority) currentSession().get(Authority.class, name);
	}
	
	@Override
	public void save(String roleName) {
		Authority authority = currentSession().get(Authority.class, roleName);
		if(authority == null) {
			authority = new Authority();
			authority.setName(roleName);
			currentSession().save(authority);
		}
		
	}

}
