package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_employment_type")
public class EmploymentType extends BaseEntity{
	
	@Column(name = "employment_code",length =5,unique=true,nullable = false)
	private String employmentCode;
	
	@Column(name = "employment_name",length =30,nullable = false)
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
