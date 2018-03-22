package com.spring5demo.demo.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Profile("devMysql")
@PropertySource("classpath:/application.properties")
public class DevMysqlDatasourceConfig implements DatasourceConfig {

	@Value("${datasource.driver-class-name:}")
	private String driverName;

	@Value("${datasource.url:}")
	private String dbUrl;

	@Value("${datasource.username:}")
	private String userName;

	@Value("${datasource.password:}")
	private String password;

	@Override
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverName);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}

}
