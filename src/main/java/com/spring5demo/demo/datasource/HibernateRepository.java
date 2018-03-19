package com.spring5demo.demo.datasource;

import java.util.Arrays;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.spring5demo.demo.domain.Todo;
import com.spring5demo.demo.repository.HibernateTodoRepository;
import com.spring5demo.demo.repository.TodoRepository;

@Profile("orm")
@Configuration
@EnableTransactionManagement
public class HibernateRepository implements RepositoryConfig {

	@Autowired
	private Environment env;

	private String dbName = "H2Dialect";

	public HibernateRepository() {
		try {
			String[] activeProfiles = env.getDefaultProfiles();
			if (activeProfiles != null) {
				if (!Arrays.asList(activeProfiles).contains("devH2")) {
					dbName = "MySQLDialect";
				}
			}
		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
		}
	}

	@Bean
	public TodoRepository todoRepository(SessionFactory sessionFactory) {
		return new HibernateTodoRepository(sessionFactory);
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setAnnotatedClasses(Todo.class);
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	@Bean
	public BeanPostProcessor persistenceTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

	Properties hibernateProperties() {//
		return new Properties() {
			{
				setProperty("hibernate.hbm2ddl.auto", "update");
				setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
				setProperty("hibernate.globally_quoted_identifiers", "true");
			}
		};
	}

}
