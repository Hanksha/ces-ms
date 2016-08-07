package com.hanksha.ces.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.hanksha.ces.data.DataPackageMaker;

@Configuration
@ComponentScan(basePackageClasses=[DataPackageMaker])
class DataConfig {

	@Bean
	@Profile('prod')
	public DataSource prodDataSource(@Value('${datasource.url}') String url,
			@Value('${datasource.username}') String username,
			@Value('${datasource.password}') String password) {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		
		return ds;
	}
	
	@Bean
	@Profile('dev')
	public DataSource devDataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScripts('classpath:schema.sql', 'classpath:data.sql')
				.build();
	}

	/*@Bean
	@Profile('test')
	public DataSource testDataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScripts('classpath:schema.sql', 'classpath:test-data.sql')
				.build();
	}*/

	@Bean
	public JdbcOperations jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	/*@Bean
	public Properties databaseProp(Environment env) {
		Properties prop = new Properties();
		
		prop.setProperty("datasource.url", env.getProperty("datasource.url"));
		prop.setProperty("datasource.username", env.getProperty("datasource.username"));
		prop.setProperty("datasource.password", env.getProperty("datasource.password"));
		prop.setProperty("datasource.driver-class-username", env.getProperty("datasource.driver-class-username"));
		
		return prop;
	}*/
}
