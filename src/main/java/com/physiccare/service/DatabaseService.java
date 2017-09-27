package com.physiccare.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.slugify.Slugify;

@Service
public class DatabaseService {

	private static final String DB_USER = "postgres";

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public String createSchema(final String name) {
		final String schema = schemaName(name);

		if (existsSchema(schema)) {
			throw new RuntimeException("Nome informado não esta disponivel");
		}

		Query nativeQuery = em.createNativeQuery("CREATE SCHEMA " + schema + " AUTHORIZATION " + DB_USER);
		nativeQuery.executeUpdate();

		return schema;
	}

	@Transactional(readOnly = true)
	public boolean existsSchema(String name) {
		final BigDecimal value = new BigDecimal(0); 
		
		em.unwrap(Session.class).doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				try (PreparedStatement stmt = connection.prepareStatement("SELECT count(schema_name) AS schemas FROM information_schema.schemata WHERE schema_name = (?1)")) {
					stmt.setString(1, name);
					
					ResultSet resultSet = stmt.executeQuery();
					resultSet.next();
					
					int schemas = resultSet.getInt("schemas");
				}
				
			}
		});
		
		
		Query nativeQuery = em
		        .createNativeQuery("SELECT schema_name FROM information_schema.schemata WHERE schema_name = (?1)");
		nativeQuery.setParameter(1, name);
		Object singleResult = nativeQuery.getSingleResult();
		int results = nativeQuery.getMaxResults();
		return results == 1;
	}

	private static String schemaName(String name) {
		String normalizedName = new Slugify().slugify(name).replaceAll("-", "_");
		PolicyFactory policy = new HtmlPolicyBuilder().toFactory();
		return policy.sanitize(normalizedName);
	}
}
