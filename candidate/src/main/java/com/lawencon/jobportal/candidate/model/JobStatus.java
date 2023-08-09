package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "t_job_status")
public class JobStatus {
	@Column(name = "status_code",length =5,unique=true,nullable = false)
	private String statusCode;
	@Column(name = "status_name",length =30,nullable = false)
	private String statusName;
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
