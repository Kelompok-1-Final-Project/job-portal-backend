package com.lawencon.jobportal.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_user")
public class User extends BaseEntity {

	@Column(name = "email", length = 30, nullable = false, unique = true)
	private String email;
	
	@Column(name = "pass", nullable = false)
	private String pass;
	
	@OneToOne
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile profile;
	
	@OneToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
