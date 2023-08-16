package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_status_process")
public class StatusProcess extends BaseEntity{
	
	@Column(name = "process_code", length = 5, nullable = false, unique = true)
	private String processCode;
	
	@Column(name = "process_name", length = 30, nullable = false)
	private String processName;

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
}
