package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_job_position")
public class JobPosition extends BaseEntity{
	
	@Column(name = "position_code", length = 5, nullable = false, unique = true)
	private String positionCode;
	
	@Column(name = "position_name", length = 30, nullable = false)
	private String positionName;

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
}
