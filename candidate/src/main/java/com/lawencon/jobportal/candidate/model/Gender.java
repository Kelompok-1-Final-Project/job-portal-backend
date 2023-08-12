package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;


@Entity
@Table(name = "t_gender")
public class Gender extends BaseEntity{
	
	@Column(name = "gender_name", length = 30, nullable = false)
	private String genderName;

	@Column(name = "gender_code", length = 5, nullable = false)
	private String genderCode;

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}
}
