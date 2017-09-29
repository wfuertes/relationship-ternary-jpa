package com.physiccare.multitenancy;

import static com.physiccare.multitenancy.MultiTenantConnectionProviderImpl.DEFAULT_TENANT_ID;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	private static Logger LOGGER = LoggerFactory.getLogger(TenantIdentifierResolver.class);

	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenantId = TenantContext.getCurrentTenant();

		LOGGER.info("resolveCurrentTenantIdentifier: " + tenantId);

		if (tenantId != null) {
			return tenantId;
		}
		return DEFAULT_TENANT_ID;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		LOGGER.info("validateExistingCurrentSessions returning true");
		return true;
	}

}
