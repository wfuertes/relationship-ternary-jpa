package com.physiccare.helper;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import com.github.slugify.Slugify;

public class DatabaseHelper {
	
	public static String schemaName(String name) {
		String normalizedName = new Slugify().slugify(name).replaceAll("-", "_");
		PolicyFactory policy = new HtmlPolicyBuilder().toFactory();
		return policy.sanitize(normalizedName);
	}
}
