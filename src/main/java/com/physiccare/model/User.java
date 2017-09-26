package com.physiccare.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "public", name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserRoleBusiness> userRoleBusinesses;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserRoleBusiness> getUserRoleBusinesses() {
		return userRoleBusinesses;
	}

	public void setUserRoleBusinesses(Set<UserRoleBusiness> userRoleBusinesses) {
		this.userRoleBusinesses = userRoleBusinesses;
	}

}
