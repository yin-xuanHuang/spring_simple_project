package com.spring5demo.demo.datasource;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import com.spring5demo.demo.repository.JdbcTodoRepository;
import com.spring5demo.demo.repository.TodoRepository;

@Profile("jdbc")
@Configuration
public class JdbcTemplateRepositoryConfig implements RepositoryConfig {
		
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public TodoRepository todoRepository(JdbcTemplate jdbcTemplate) {
		return new JdbcTodoRepository(jdbcTemplate);
	}
	
}
