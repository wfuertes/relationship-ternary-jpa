package com.physiccare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.physiccare.helper.DatabaseHelper;

@Service
public class DatabaseService {

	private static final String DB_USER = "postgres";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public String createSchema(final String name) {
		final String schema = DatabaseHelper.schemaName(name);
		jdbcTemplate.execute("CREATE SCHEMA " + schema + " AUTHORIZATION " + DB_USER);
		return schema;
	}

	@Transactional(readOnly = true)
	public int countSchema(String name) {
		final String sql = "SELECT COUNT(schema_name) AS schemas FROM information_schema.schemata WHERE schema_name = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, name);
	}
}
