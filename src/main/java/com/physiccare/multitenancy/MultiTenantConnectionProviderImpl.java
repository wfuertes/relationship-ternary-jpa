package com.physiccare.multitenancy;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

	private static final long serialVersionUID = 8456514862061038855L;

	public static final String DEFAULT_TENANT_ID = "public";

	@Autowired
	private DataSource dataSource;

	@Override
	public Connection getAnyConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public Connection getConnection(String tenantIdentifie) throws SQLException {
		final String tenantIdentifier = TenantContext.getCurrentTenant();
		final Connection connection = getAnyConnection();

		try (Statement stmt = connection.createStatement()) {
			if (tenantIdentifier != null) {
				stmt.execute(String.format("SET SCHEMA '%s';", tenantIdentifier));
			} else {
				stmt.execute(String.format("SET SCHEMA '%s';", DEFAULT_TENANT_ID));
			}
		} catch (SQLException e) {
			throw new HibernateException("Problem setting schema to " + tenantIdentifier, e);
		}
		
		return connection;
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		try {
			connection.createStatement().execute("USE " + DEFAULT_TENANT_ID);
		} catch (SQLException e) {
			throw new HibernateException("Problem setting schema to " + tenantIdentifier, e);
		}
		connection.close();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean isUnwrappableAs(Class unwrapType) {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return null;
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return true;
	}
}
