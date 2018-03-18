package com.spring5demo.demo.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
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
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverName);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer() {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScripts(new ClassPathResource("/schema-mysql.sql"), new ClassPathResource("/data-mysql.sql"));

		DataSourceInitializer dataSourceInitialize = new DataSourceInitializer();
		dataSourceInitialize.setDataSource(dataSource());
		dataSourceInitialize.setDatabasePopulator(resourceDatabasePopulator);
		return dataSourceInitialize;
	}

}
