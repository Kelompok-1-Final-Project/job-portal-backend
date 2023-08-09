package com.lawencon.jobportal.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Profile;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_candidate")
public class Candidate extends BaseEntity{

	@Column(name = "email", unique=true, length=30, nullable=false)
	private String email;
	
	@Column(name = "pass", nullable=false)
	private String password;
	
	@OneToOne
	@JoinColumn(name = "profile_id")
	private Profile profile;

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

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
