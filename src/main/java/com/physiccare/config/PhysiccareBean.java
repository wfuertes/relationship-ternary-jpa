package com.physiccare.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class PhysiccareBean {

	@Bean
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(DataSource dataSource) {
		
		return new NamedParameterJdbcTemplate(dataSource);
	}
}
