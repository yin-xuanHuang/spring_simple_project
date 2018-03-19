package com.spring5demo.demo.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.spring5demo.demo.datasource.DevEmbeddedDatasourceConfig;
import com.spring5demo.demo.datasource.DevMysqlDatasourceConfig;
import com.spring5demo.demo.datasource.HibernateRepository;
import com.spring5demo.demo.datasource.JdbcTemplateRepositoryConfig;
import com.spring5demo.demo.datasource.ProductionDatasourceConfig;

@Configuration
@EnableWebSecurity
@Import({ ProductionDatasourceConfig.class, DevEmbeddedDatasourceConfig.class, DevMysqlDatasourceConfig.class,
		JdbcTemplateRepositoryConfig.class, HibernateRepository.class })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.passwordEncoder(passwordEncoder())
			.dataSource(dataSource)
			.usersByUsernameQuery(
					"SELECT username, password, enabled FROM users WHERE username =?")
			.authoritiesByUsernameQuery(
					"SELECT username, authority_name FROM user_authority WHERE username = ?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/todos*").hasAuthority("ROLE_USER")
			.antMatchers(HttpMethod.DELETE, "/todos*").hasAuthority("ROLE_ADMIN")
		.and()
			.httpBasic().disable()
			.formLogin().loginPage("/login")
				.loginProcessingUrl("/login")
				.failureUrl("/login?error=true")
				.permitAll()
		.and()
			.logout()
				.logoutSuccessUrl("/logout-success");
	}

}
