package com.spring5demo.demo.datasource;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@PropertySource("classpath:/application.properties")
public class ProductionDatasourceConfig implements DatasourceConfig {

	@Value("${spring.datasource.driver-class-name:}")
	private String driverName;

	@Value("${spring.datasource.url:}")
	private String dbUrl;

	@Value("${spring.datasource.username:}")
	private String userName;

	@Value("${spring.datasource.password:}")
	private String password;

	@Override
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverName);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setInitialSize(5);
		dataSource.setMaxActive(10);
		return dataSource;
	}
}
