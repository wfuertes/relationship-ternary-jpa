package com.physiccare.multitenancy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TenantInterceptor implements HandlerInterceptor {

	private static Logger LOGGER = LoggerFactory.getLogger(TenantInterceptor.class.getName());

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object obj) throws Exception {
		LOGGER.info("preHandle: Intercepting request to read X-TENANT");

		final String tenant = req.getHeader("X-TENANT");
		TenantContext.setCurrentTenant(tenant);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object obj, ModelAndView error)
			throws Exception {

		LOGGER.info("postHandle: cleaning currentTenant");
		TenantContext.clear();
	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object obj, Exception error)
			throws Exception {

		LOGGER.info("afterCompletion: Request is finished!!!!");
	}
}
