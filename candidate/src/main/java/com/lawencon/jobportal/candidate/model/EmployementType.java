package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_employement_type")
public class EmployementType {
	
	@Column(name = "employement_code",length =5,unique=true,nullable = false)
	private String employementCode;
	
	@Column(name = "employement_name",length =30,nullable = false)
	private String employementName;
	
	public String getEmployementName() {
		return employementName;
	}
	
	public void setEmployementName(String employementName) {
		this.employementName = employementName;
	}
	
	public String getEmployementCode() {
		return employementCode;
	}
	
	public void setEmployementCode(String employementCode) {
		this.employementCode = employementCode;
	}
}
