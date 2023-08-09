package com.lawencon.jobportal.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_employment_type")
public class EmploymentType {

	@Column(name = "employment_code", length = 5, nullable = false, unique = true)
	private String employmentCode;

	@Column(name = "employment_name", length = 30, nullable = false)
	private String employmentName;

	public String getEmploymentCode() {
		return employmentCode;
	}

	public void setEmploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
	}

	public String getEmploymentName() {
		return employmentName;
	}

	public void setEmploymentName(String employmentName) {
		this.employmentName = employmentName;
	}

}
