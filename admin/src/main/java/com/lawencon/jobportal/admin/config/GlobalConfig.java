package com.lawencon.jobportal.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GlobalConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
//	@Bean(name = "initTable")
//	public SpringLiquibase initTable(DataSource dataSource) {
//		final SpringLiquibase springLiquibase = new SpringLiquibase();
//		springLiquibase.setChangeLog("/db/migration/script/init_table_v001.sql");
//		springLiquibase.setDataSource(dataSource);
//		return springLiquibase;
//	}
//	
//	@Bean(name = "initData")
//	@DependsOn("initTable")
//	public SpringLiquibase initData(DataSource dataSource) {
//		final SpringLiquibase springLiquibase = new SpringLiquibase();
//		springLiquibase.setChangeLog("/db/migration/script/init_data_v001.sql");
//		springLiquibase.setDataSource(dataSource);
//		return springLiquibase;
//	}
	
	
}
