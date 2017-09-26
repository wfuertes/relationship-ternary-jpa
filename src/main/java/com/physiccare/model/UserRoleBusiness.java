package com.physiccare.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "public", name = "users_roles_businesses")
public class UserRoleBusiness {

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
			@AttributeOverride(name = "roleId", column = @Column(name = "role_id", nullable = false, length = 256)),
			@AttributeOverride(name = "businessId", column = @Column(name = "business_id", nullable = false)) })
	private UserRoleBusiness.Id id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "business_id", nullable = false, insertable = false, updatable = false)
	private Business business;

	public UserRoleBusiness.Id getId() {
		return id;
	}

	public void setId(UserRoleBusiness.Id id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	@Embeddable
	public static class Id implements Serializable {
		
		private static final long serialVersionUID = -1099626893930080066L;
		

		@Column(name = "user_id", nullable = false)
		private Long userId;

		@Column(name = "role_id", nullable = false, length = 256)
		private String roleId;

		@Column(name = "business_id", nullable = false)
		private Long businessId;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getBusinessId() {
			return businessId;
		}

		public void setBusinessId(Long businessId) {
			this.businessId = businessId;
		}

		public String getRoleId() {
			return roleId;
		}

		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}
	}
}
