package com.physiccare.dto;

import java.util.ArrayList;
import java.util.List;

public class UserCreated {

	private Long id;
	private String name;
	private String email;
	private List<Urbs> urbs = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Urbs> getUrbs() {
		return urbs;
	}

	public void setUrbs(List<Urbs> urbs) {
		this.urbs = urbs;
	}

	public static class Urbs {
		private String business;
		private String schema;
		private String role;

		public Urbs() {
		}

		public Urbs(String business, String schema, String role) {
			this.business = business;
			this.schema = schema;
			this.role = role;
		}

		public String getBusiness() {
			return business;
		}

		public void setBusiness(String business) {
			this.business = business;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getSchema() {
			return schema;
		}

		public void setSchema(String schema) {
			this.schema = schema;
		}

	}
}
