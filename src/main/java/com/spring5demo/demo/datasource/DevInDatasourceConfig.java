package com.spring5demo.demo.datasource;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

@Component
@Profile("devH2")
public class DevInDatasourceConfig implements DatasourceConfig {

	@Override
	@Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("board")
                .addScript("classpath:/schema-h2.sql")
                .addScript("classpath:/data-h2.sql")
                .build();
    }

}
