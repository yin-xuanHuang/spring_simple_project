package com.spring5demo.demo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spring5demo.demo.domain.Authority;

public class HibernateAuthorityRepository implements AuthorityRepository {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private SessionFactory sessionFactory;

	public HibernateAuthorityRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	

	@Override
	public Authority findOneByName(String roleName) {
		 return (Authority) currentSession()
								.createQuery("from Authority where name=?")
								.setParameter(0, roleName)
								.uniqueResult();

		 
	}
	
	@Override
	public void save(String roleName) {
		
		Authority authority = findOneByName(roleName);
		
		if(authority == null) {
			authority = new Authority();
			authority.setName(roleName);
			currentSession().save(authority);
		}
		
	}

}
