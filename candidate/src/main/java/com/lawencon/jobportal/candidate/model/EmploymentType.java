package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_employment_type")
public class EmploymentType {
	
	@Column(name = "employment_code",length =5,unique=true,nullable = false)
	private String employmentCode;
	
	@Column(name = "employment_name",length =30,nullable = false)
	private String employmentName;
	
	public String getemploymentName() {
		return employmentName;
	}
	
	public void setemploymentName(String employmentName) {
		this.employmentName = employmentName;
	}
	
	public String getemploymentCode() {
		return employmentCode;
	}
	
	public void setemploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
	}
}
