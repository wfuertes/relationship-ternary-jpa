package com.physiccare.multitenancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantContext {
	
	private static Logger LOGGER = LoggerFactory.getLogger(TenantContext.class.getName());
	
	private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

	public static void setCurrentTenant(String tenant) {
		LOGGER.info("Setting tenant to " + tenant);
		currentTenant.set(tenant);
	}

	public static String getCurrentTenant() {
		return currentTenant.get();
	}

	public static void clear() {
		currentTenant.set(null);
	}
}
